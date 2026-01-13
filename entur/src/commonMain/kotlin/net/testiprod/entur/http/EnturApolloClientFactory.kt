package net.testiprod.entur.http

import com.apollographql.apollo.ApolloClient
import com.apollographql.ktor.ktorClient
import io.ktor.client.HttpClient
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL

object EnturApolloClientFactory {

    fun create(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        val client = EnturHttpClientFactory.create(companyName, appName)

        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .ktorClient(client)
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
}
