package net.testiprod.entur.http

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.HttpEngine
import com.apollographql.ktor.ktorClient
import io.ktor.client.HttpClient
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL

object EnturApolloClientFactory {

    fun create(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
        httpClient: HttpClient? = null,
        configure: ApolloClient.Builder.() -> Unit = {},
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        val client = httpClient ?: EnturHttpClientFactory.create(companyName, appName)

        val enturClientName = "$companyName-$appName"

        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .ktorClient(client)
            .addHttpHeader("Content-Type", "application/json")
            .addHttpHeader("ET-Client-Name", enturClientName)
            .apply(configure)
            .build()
    }

    fun createWithCustomEngine(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
        httpEngine: HttpEngine,
        configure: ApolloClient.Builder.() -> Unit = {},
    ): ApolloClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        return ApolloClient.Builder()
            .serverUrl(serverUrl)
            .httpEngine(httpEngine)
            .apply(configure)
            .build()
    }
}
