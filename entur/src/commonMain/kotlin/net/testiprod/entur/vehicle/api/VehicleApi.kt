package net.testiprod.entur.vehicle.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesQuery
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesSubscription
import net.testiprod.entur.common.VEHICLES_BASE_URL
import net.testiprod.entur.common.VEHICLE_SUBSCRIPTION_BASE_URL
import net.testiprod.entur.common.exceptions.EnturResponseException
import net.testiprod.entur.http.EnturApolloClientFactory
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.logging.EnturLog
import net.testiprod.entur.logging.HttpLogLevel
import net.testiprod.entur.vehicle.models.Vehicle
import net.testiprod.entur.vehicle.toDomain

class VehicleApi(private val vehicleClient: ApolloClient) : AutoCloseable {

    private val logger = EnturLog.logger<VehicleApi>()

    constructor(
        companyName: String,
        appName: String,
        logLevel: HttpLogLevel = HttpLogLevel.ALL,
        baseUrl: String = VEHICLES_BASE_URL,
        baseSubscriptionUrl: String = VEHICLE_SUBSCRIPTION_BASE_URL,
    ) : this(
        EnturApolloClientFactory.createVehicleClient(
            companyName,
            appName,
            baseUrl,
            baseSubscriptionUrl,
            logLevel,
        ),
    )

    suspend fun fetchVehicles(serviceJourneyId: String): EnturResult<List<Vehicle>> = try {
        val response = vehicleClient.query(VehiclesQuery(serviceJourneyId)).execute()
        EnturResult.Success(mapQueryResponse(response))
    } catch (e: CancellationException) {
        logger.w("Vehicle API query cancelled: ${e.message}")
        throw e
    } catch (e: Throwable) {
        logger.w("Vehicle API query failed: ${e.message}", e)
        EnturResult.Error(e)
    }

    fun subscribeToVehicleUpdates(
        serviceJourneyId: String,
        retryPolicy: RetryPolicy = RetryPolicy.Default,
    ): Flow<List<Vehicle>> = vehicleClient.subscription(VehiclesSubscription(serviceJourneyId))
        .toFlow()
        .map { mapSubscriptionResponse(it) }
        .retryWhen { throwable, attempt ->
            if (attempt >= retryPolicy.maxAttempts || !retryPolicy.shouldRetry(throwable)) {
                logger.w("Not retrying vehicle subscription after attempt $attempt", throwable)
                false
            } else {
                logger.w("Retrying vehicle subscription after attempt $attempt", throwable)
                delay(retryPolicy.delay(attempt))
                true
            }
        }

    private fun mapQueryResponse(data: ApolloResponse<VehiclesQuery.Data>): List<Vehicle> =
        mapResponse(data) { it.vehicles?.mapNotNull { it?.toDomain() } }

    private fun mapSubscriptionResponse(data: ApolloResponse<VehiclesSubscription.Data>): List<Vehicle> =
        mapResponse(data) { it.vehicles?.mapNotNull { it?.toDomain() } }

    private fun <T : Operation.Data> mapResponse(
        response: ApolloResponse<T>,
        extractVehicles: (T) -> List<Vehicle>?,
    ): List<Vehicle> {
        response.exception?.let {
            throw it
        }
        response.errors?.let {
            throw EnturResponseException("Vehicle API errors: ${it.joinToString()}", response.exception)
        }
        return response.data?.let { extractVehicles(it) }
            ?: throw EnturResponseException("Got neither data, nor errors from Entur vehicle query.", null)
    }

    override fun close() {
        vehicleClient.close()
    }
}
