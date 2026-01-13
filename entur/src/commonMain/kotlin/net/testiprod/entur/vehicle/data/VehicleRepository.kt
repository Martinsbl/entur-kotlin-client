package net.testiprod.entur.vehicle.data

import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.vehicle.domain.Vehicle
import kotlin.time.Clock

class VehicleRepository(
    httpClient: HttpClient,
    private val vehicleTimeout: Long,
) : VehicleRepositoryInterface {

    private val vehicleDataSource = VehicleDataSource(httpClient)

    override fun getVehicleFlow(
        codeSpaceId: String?,
        lineRef: String?,
    ): Flow<List<Vehicle>> {
        val initialQuery = flow {
            val response = vehicleDataSource.queryVehicles(codeSpaceId, lineRef)
            when (response) {
                is EnturResult.Success -> {
                    emit(response.data)
                }

                is EnturResult.Error -> {
                    onError(response.exception, null)
                }
            }
        }
        val subscription =
            vehicleDataSource.subscribeToVehicleUpdates(codeSpaceId, lineRef, ::onError)
        return merge(initialQuery, subscription)
            .flowOn(Dispatchers.Default)
            .filter { it.isNotEmpty() }
            .map {
                filterVehicles(it)
            }
    }

    private fun onError(
        throwable: Throwable?,
        attempt: Long?,
    ) {
        // TODO
        throwable?.printStackTrace()
    }

    private val vehicleMap = mutableMapOf<String, Vehicle>()

    private fun filterVehicles(vehicles: List<Vehicle>): List<Vehicle> {
        val now = Clock.System.now()
        val expiryTime = now.toEpochMilliseconds() - (vehicleTimeout * 60_000)
        val filtered = vehicles.filter { it.lastUpdated.toEpochMilliseconds() > expiryTime }

        filtered.forEach {
            useOldBearing(it)
        }

        return vehicleMap.values.toList()
            .sortedBy { it.serviceJourneyId }
    }

    private fun useOldBearing(it: Vehicle) {
        val newBearing = it.bearing
        val oldBearing = vehicleMap[it.serviceJourneyId]?.bearing
        if (newBearing == 0.0 && oldBearing != null) {
            vehicleMap[it.serviceJourneyId] = it.copy(bearing = oldBearing)
        } else {
            vehicleMap[it.serviceJourneyId] = it
        }
    }
}
