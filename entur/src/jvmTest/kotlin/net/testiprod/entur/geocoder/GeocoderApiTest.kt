package net.testiprod.entur.geocoder

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import net.testiprod.entur.http.EnturResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GeocoderApiTest {

    @Test
    fun `fetchStopPlaces by text returns filtered venues`() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = getResourceFileAsText("mock_responses/geocoder-autocomplete-response.json"),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val api = GeocoderApi(client)
        val result = api.fetchStopPlaces(text = "Oslo", size = 20)
        println(result)
        assertTrue(result is EnturResult.Success)
        assertEquals(20, result.data.size)
    }
}
