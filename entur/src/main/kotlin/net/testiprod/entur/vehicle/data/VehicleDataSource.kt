package net.testiprod.entur.vehicle.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import com.apollographql.ktor.ktorClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesQuery
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesSubscription
import net.testiprod.entur.common.exceptions.EnturResponseException
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.vehicle.data.QueryMarshaller.toDomain
import net.testiprod.entur.vehicle.data.SubscriptionMarshaller.toDomain
import net.testiprod.entur.vehicle.domain.Vehicle

internal class VehicleDataSource(httpClient: HttpClient) {

    private val vehicleClient = ApolloClient.Builder()
        .serverUrl(SERVER_BASE_URL)
        .webSocketServerUrl(VEHICLE_BASE_URL)
        .webSocketIdleTimeoutMillis(5_000)
        .ktorClient(httpClient)
        .build()

    suspend fun queryVehicles(
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

    companion object {
        private const val SERVER_BASE_URL = "https://api.entur.io/realtime/v1/vehicles/graphql"
        private const val VEHICLE_BASE_URL = "wss://api.entur.io/realtime/v1/vehicles/subscriptions"
    }
}
