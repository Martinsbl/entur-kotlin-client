package net.testiprod.entur.http

import com.apollographql.apollo.ApolloClient
import com.apollographql.ktor.ktorClient
import io.ktor.client.HttpClient
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.common.VEHICLES_BASE_URL
import net.testiprod.entur.common.VEHICLE_SUBSCRIPTION_BASE_URL

object EnturApolloClientFactory {

    fun create(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        val httpClient = EnturHttpClientFactory.create(companyName, appName)

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
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }
        val httpClient = EnturHttpClientFactory.create(companyName, appName)
        return ApolloClient.Builder()
            .serverUrl(baseUrl)
            .webSocketServerUrl(baseSubscriptionUrl)
            .webSocketIdleTimeoutMillis(5_000)
            .ktorClient(httpClient)
            .build()
    }

    fun createVehicleClient(
        httpClient: HttpClient,
        baseUrl: String = VEHICLES_BASE_URL,
        baseSubscriptionUrl: String = VEHICLE_SUBSCRIPTION_BASE_URL,
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(baseUrl)
            .webSocketServerUrl(baseSubscriptionUrl)
            .webSocketIdleTimeoutMillis(5_000)
            .ktorClient(httpClient)
            .build()
    }
}
