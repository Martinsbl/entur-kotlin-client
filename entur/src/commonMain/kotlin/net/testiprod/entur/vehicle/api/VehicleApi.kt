package net.testiprod.entur.vehicle.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
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
import net.testiprod.entur.vehicle.models.Vehicle
import net.testiprod.entur.vehicle.toDomain

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

    suspend fun fetchVehicles(
        codeSpaceId: String?,
        lineRef: String?,
    ): EnturResult<List<Vehicle>> {
        val response = vehicleClient.query(
            VehiclesQuery(
                Optional.presentIfNotNull(codeSpaceId),
                Optional.presentIfNotNull(lineRef),
            ),
        ).executeV3()

        return EnturResult.Success(mapQueryResponse(response))
    }

    fun subscribeToVehicleUpdates(
        codeSpaceId: String?,
        lineRef: String?,
        onRetry: (Throwable, Long) -> (Unit),
    ): Flow<List<Vehicle>> {
        return vehicleClient.subscription(
            VehiclesSubscription(
                Optional.presentIfNotNull(codeSpaceId),
                Optional.presentIfNotNull(lineRef),
            ),
        )
            .toFlowV3()
            .retryWhen { throwable, attempt ->
                onRetry.invoke(throwable, attempt)
                delay(attempt * 1000)
                true
            }
            .map { mapSubscriptionResponse(it) }
    }

    private fun mapQueryResponse(data: ApolloResponse<VehiclesQuery.Data>): List<Vehicle> {
        data.errors?.let {
            throw EnturResponseException("Vehicle query errors=${it.joinToString()}", null)
        }
        data.data?.vehicles?.let { vehicles ->
            return vehicles.mapNotNull { it?.toDomain() }
        }
        throw EnturResponseException("Got neither data, nor errors from Entur vehicle query.", null)
    }

    private fun mapSubscriptionResponse(data: ApolloResponse<VehiclesSubscription.Data>): List<Vehicle> {
        data.errors?.let {
            throw EnturResponseException("Vehicle subscription errors=${it.joinToString()}", null)
        }
        data.data?.vehicles?.let { vehicles ->
            return vehicles.mapNotNull { it?.toDomain() }
        }
        throw EnturResponseException(
            "Got neither data, nor errors from Entur vehicle subscription.",
            null,
        )
    }
}
