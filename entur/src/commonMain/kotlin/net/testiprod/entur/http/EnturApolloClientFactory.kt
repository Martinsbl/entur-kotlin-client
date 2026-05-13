package net.testiprod.entur.http

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.exception.ApolloWebSocketClosedException
import com.apollographql.apollo.network.ws.GraphQLWsProtocol
import com.apollographql.ktor.ktorClient
import io.ktor.client.HttpClient
import kotlin.math.min
import kotlinx.coroutines.delay
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.common.VEHICLES_BASE_URL
import net.testiprod.entur.common.VEHICLE_SUBSCRIPTION_BASE_URL
import net.testiprod.entur.logging.HttpLogLevel

object EnturApolloClientFactory {

    fun create(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
        logLevel: HttpLogLevel = HttpLogLevel.INFO,
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        val httpClient = EnturHttpClientFactory.create(companyName, appName, logLevel)

        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .ktorClient(httpClient)
            .build()
    }

    fun create(
        httpClient: HttpClient,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .ktorClient(httpClient)
            .build()
    }

    fun createVehicleClient(
        companyName: String,
        appName: String,
        baseUrl: String = VEHICLES_BASE_URL,
        baseSubscriptionUrl: String = VEHICLE_SUBSCRIPTION_BASE_URL,
        logLevel: HttpLogLevel = HttpLogLevel.INFO,
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }
        val httpClient = EnturHttpClientFactory.createVehicleSubscriptionClient(companyName, appName, logLevel)
        return createVehicleClient(httpClient, baseUrl, baseSubscriptionUrl)
    }

    fun createVehicleClient(
        httpClient: HttpClient,
        baseUrl: String = VEHICLES_BASE_URL,
        baseSubscriptionUrl: String = VEHICLE_SUBSCRIPTION_BASE_URL,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(baseUrl)
            .webSocketServerUrl(baseSubscriptionUrl)
            .webSocketIdleTimeoutMillis(60_000)
            .wsProtocol(GraphQLWsProtocol.Factory())
            .webSocketReopenWhen { throwable, attempt ->
                if (throwable is ApolloWebSocketClosedException || throwable is ApolloNetworkException) {
                    delay(min((attempt + 1) * 1_000, 30_000L))
                    true
                } else {
                    false
                }
            }
            .ktorClient(httpClient)
            .build()
    }
}
