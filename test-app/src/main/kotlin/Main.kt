package net.testiprod

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.testiprod.entur.common.DRAMMEN_BUSS_STASJON
import net.testiprod.entur.common.OSLO_S
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.client.StopPlaceApi
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import net.testiprod.entur.journeyplanner.stopplace.service.StopPlaceService
import kotlin.time.Duration.Companion.seconds

fun main() {
    val stopPlaceApi = StopPlaceApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )
    val stopPlaceService = StopPlaceService(stopPlaceApi)

    runBlocking {
        val stopPlaces = stopPlaceApi.fetchStopPlaceQuay(OSLO_S)
        println(stopPlaces.toPrettyString())

        stopPlaceService.observeStopPlace(
            DRAMMEN_BUSS_STASJON,
            refreshInterval = 20.seconds,
        ).collect {
            println(it.toPrettyString())
        }

        var counter = 0
        while (counter++ < 10) {
            delay(10_000L)
            println("Still running...")
        }
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
