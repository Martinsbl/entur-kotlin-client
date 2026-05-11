package net.testiprod.entur.vehicle.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.retryWhen
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesQuery
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesSubscription
import net.testiprod.entur.common.VEHICLES_BASE_URL
import net.testiprod.entur.common.VEHICLE_SUBSCRIPTION_BASE_URL
import net.testiprod.entur.common.exceptions.EnturResponseException
import net.testiprod.entur.http.EnturApolloClientFactory
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.vehicle.models.Vehicle
import net.testiprod.entur.vehicle.toDomain
import kotlin.math.min

class VehicleApi(private val vehicleClient: ApolloClient) {
    constructor(
        companyName: String,
        appName: String,
        baseUrl: String = VEHICLES_BASE_URL,
        baseSubscriptionUrl: String = VEHICLE_SUBSCRIPTION_BASE_URL,
    ) : this(
        EnturApolloClientFactory.createVehicleClient(
            companyName,
            appName,
            baseUrl,
            baseSubscriptionUrl,
        ),
    )

    suspend fun fetchVehicles(serviceJourneyId: String): EnturResult<List<Vehicle>> {
        val response = vehicleClient.query(VehiclesQuery(serviceJourneyId)).execute()
        return EnturResult.Success(mapQueryResponse(response))
    }

    fun subscribeToVehicleUpdates(
        serviceJourneyId: String,
        onRetry: suspend (Throwable, Long) -> Boolean = { t, l -> defaultRetry(l) },
    ): Flow<List<Vehicle>> {
        return vehicleClient.subscription(VehiclesSubscription(serviceJourneyId))
            .toFlow()
            .map {
                mapSubscriptionResponse(it)
            }
            .retryWhen { throwable, attempt ->
                onRetry(throwable, attempt)
            }
    }

    private suspend fun defaultRetry(attempt: Long): Boolean {
        delay(min(attempt * 1000, 5_000L))
        return true
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
            throw EnturResponseException("Entur exception", it)
        }
        response.errors?.let {
            throw EnturResponseException("Vehicle errors: ${it.joinToString()}", response.exception)
        }
        return response.data?.let { extractVehicles(it) }
            ?: throw EnturResponseException("Got neither data, nor errors from Entur vehicle query.", null)
    }
}
