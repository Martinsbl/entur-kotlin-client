package net.testiprod.entur.http

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json

object EnturHttpClientFactory {

    /**
     * Creates an instance of [HttpClient] configured for Entur API usage.
     * companyName and appName are used to set the "ET-Client-Name" header as speciefied
     * here https://developer.entur.org/pages-intro-authentication.
     * @param companyName The name of the company using the client. Must not be blank.
     * @param appName The name of the application using the client. Must not be blank
     * @param configure Optional lambda to further configure the [HttpClient].
     */
    fun create(
        companyName: String,
        appName: String,
        configure: HttpClientConfig<*>.() -> Unit = {},
    ): HttpClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        val enturClientName = "$companyName-$appName"

        return HttpClient {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.entur.io"
                }
                header("ET-Client-Name", enturClientName)
            }

            install(Logging) {
                // TODO Configurable logging level?
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json()
            }

            configure()
        }
    }
}
