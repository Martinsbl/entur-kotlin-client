package net.testiprod.entur.http

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import net.testiprod.entur.logging.EnturLog
import net.testiprod.entur.logging.HttpLogLevel
import net.testiprod.entur.logging.HttpLogLevel.Companion.toKtorLoglevel

object EnturHttpClientFactory {

    /**
     * Creates an instance of [HttpClient] configured for Entur API usage.
     * companyName and appName are used to set the "ET-Client-Name" header as specified
     * here https://developer.entur.org/pages-intro-authentication.
     * @param companyName The name of the company using the client. Must not be blank.
     * @param appName The name of the application using the client. Must not be blank
     * @param configure Optional lambda to further configure the [HttpClient].
     */
    fun create(
        companyName: String,
        appName: String,
        logLevel: HttpLogLevel = HttpLogLevel.INFO,
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
                logger = object : Logger {
                    private val enturLogger = EnturLog.logger("EnturHttp")
                    override fun log(message: String) {
                        enturLogger.d(message)
                    }
                }
                level = logLevel.toKtorLoglevel()
            }

            install(ContentNegotiation) {
                json()
            }
            configure()
        }
    }

    /**
     * Creates an instance of [HttpClient] that supports WebSockets for Vehicle Subscriptions.
     * companyName and appName are used to set the "ET-Client-Name" header as specified
     * here https://developer.entur.org/pages-intro-authentication.
     * @param companyName The name of the company using the client. Must not be blank.
     * @param appName The name of the application using the client. Must not be blank
     * @param configure Optional lambda to further configure the [HttpClient].
     */
    fun createVehicleSubscriptionClient(
        companyName: String,
        appName: String,
        logLevel: HttpLogLevel = HttpLogLevel.INFO,
        configure: HttpClientConfig<*>.() -> Unit = {},
    ): HttpClient {
        return create(companyName, appName, logLevel) {
            install(WebSockets)
            configure()
        }
    }
}
