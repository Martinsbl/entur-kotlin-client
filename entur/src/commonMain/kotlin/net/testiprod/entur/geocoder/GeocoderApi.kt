package net.testiprod.entur.geocoder

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import net.testiprod.entur.common.models.Feature
import net.testiprod.entur.common.models.FeatureResult
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.http.doHttpRequest
import net.testiprod.entur.http.transformResult

/**
 * Geocoder API doc: https://developer.entur.org/pages-geocoder-api
 */
class GeocoderApi(private val httpClient: HttpClient) {

    suspend fun fetchStopPlaces(
        lat: Double,
        lon: Double,
        radius: Int = 20,
    ): EnturResult<String> = doHttpRequest<String> {
        httpClient.get("/geocoder/v1/reverse") {
            url {
                parameters.append("point.lat", lat.toString())
                parameters.append("point.lon", lon.toString())
                parameters.append("boundary.circle.radius", radius.toString())
                parameters.append("layers", "venue")
            }
        }
    }

    suspend fun fetchStopPlaces(
        text: String,
        size: Int = 20,
        multiModal: MultiModal = MultiModal.CHILD,
    ): EnturResult<List<Feature>> = doHttpRequest<FeatureResult> {
        httpClient.get("/geocoder/v1/autocomplete") {
            url {
                parameters.append("text", text)
                parameters.append("size", size.toString())
                parameters.append("lang", "no")
                parameters.append("multiModal", multiModal.value)
            }
        }
    }.transformResult {
        it.features.filterStopPlaces()
    }

    private fun List<Feature>.filterStopPlaces(): List<Feature> = this.filter {
        it.properties.layer.contains("venue")
    }
}
