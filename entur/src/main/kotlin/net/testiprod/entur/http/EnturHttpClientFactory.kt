package net.testiprod.entur.http

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json

object EnturHttpClientFactory {

    fun create(
        companyName: String,
        appName: String,
        engine: HttpClientEngine? = null,
        configure: HttpClientConfig<*>.() -> Unit = {},
    ): HttpClient {
        require(companyName.isNotBlank()) { "'companyName' cannot be blank" }
        require(appName.isNotBlank()) { "'appName' cannot be blank" }

        return HttpClient(engine ?: CIO.create()) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.entur.io"
                }
//                header("Content-Type", "application/json")
//                header("ET-Client-Name", enturtClientName)
            }

            install(ContentNegotiation) {
                json()
            }

            configure()
        }
    }
}
