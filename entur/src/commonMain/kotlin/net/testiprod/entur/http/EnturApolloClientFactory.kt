package net.testiprod.entur.http

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.exception.ApolloWebSocketClosedException
import com.apollographql.apollo.network.ws.GraphQLWsProtocol
import com.apollographql.ktor.ktorClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.common.VEHICLES_BASE_URL
import net.testiprod.entur.common.VEHICLE_SUBSCRIPTION_BASE_URL
import net.testiprod.entur.logging.EnturLog
import net.testiprod.entur.logging.HttpLogLevel
import kotlin.time.Duration.Companion.seconds

object EnturApolloClientFactory {

    private val logger = EnturLog.logger<EnturApolloClientFactory>()

    fun create(
        enturClientName: String,
        baseUrl: String = JOURNEY_PLANNER_BASE_URL,
        logLevel: HttpLogLevel = HttpLogLevel.INFO,
    ): ApolloClient {
        require(enturClientName.isNotBlank()) { "'enturClientName' cannot be blank" }

        val httpClient = EnturHttpClientFactory.create(enturClientName, logLevel)

        return ApolloClient.Builder()
            .serverUrl(baseUrl)
            .ktorClient(httpClient)
            .build()
    }

    fun create(
        httpClient: HttpClient,
        baseUrl: String = JOURNEY_PLANNER_BASE_URL,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(baseUrl)
            .ktorClient(httpClient)
            .build()
    }

    fun createVehicleClient(
        enturClientName: String,
        baseUrl: String = VEHICLES_BASE_URL,
        baseSubscriptionUrl: String = VEHICLE_SUBSCRIPTION_BASE_URL,
        logLevel: HttpLogLevel = HttpLogLevel.INFO,
    ): ApolloClient {
        require(enturClientName.isNotBlank()) { "'enturClientName' cannot be blank" }
        val httpClient = EnturHttpClientFactory.createVehicleSubscriptionClient(enturClientName, logLevel)
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
            .webSocketReopenWhen(::tryToReopen)
            .ktorClient(httpClient)
            .build()
    }

    private suspend fun tryToReopen(
        throwable: Throwable,
        attempt: Long,
        maxAttempts: Int = 3,
    ): Boolean = if (throwable is ApolloWebSocketClosedException || throwable is ApolloNetworkException) {
        delay((attempt + 1).seconds)
        val retrying = attempt <= maxAttempts
        logger.w(
            "WebSocket connection closed unexpectedly (attempt: $attempt, retrying: $retrying)",
            throwable,
        )
        retrying
    } else {
        logger.w(
            "WebSocket connection error that is not recoverable (attempt $attempt). Not retrying.",
            throwable,
        )
        false
    }
}
