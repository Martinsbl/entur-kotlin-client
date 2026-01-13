package net.testiprod

import kotlinx.coroutines.runBlocking
import net.testiprod.entur.common.OSLO_S
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.client.StopPlaceApi

fun main() {
    val stopPlaceApi = StopPlaceApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )

    // Create a coroutine scope to call suspend functions
    runBlocking {
        val stopPlaces = stopPlaceApi.fetchStopPlaceQuay(OSLO_S)
        println(stopPlaces.toPrettyString())
    }
}

private fun EnturResult<StopPlaceQuay>.toPrettyString(): String {
    when (this) {
        is EnturResult.Success -> {
            val departures = data.estimatedCalls.joinToString(separator = "\n") { call ->
                "to ${call.destinationDisplay.frontText} at ${call.expectedDepartureTime}"
            }
            return "Stop Place: ${data.name} (ID: ${data.id})\nDepartures:\n$departures"
        }

        is EnturResult.Error -> {
            return "Error fetching stop places: $this"
        }
    }
}
