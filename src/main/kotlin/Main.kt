package net.testiprod

import kotlinx.coroutines.runBlocking
import net.testiprod.entur.common.OSLO_S
import net.testiprod.entur.journeyplanner.stopplace.client.StopPlaceApi

fun main() {
    val stopPlaceApi = StopPlaceApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )

    // Create a coroutine scope to call suspend functions
    runBlocking {
        val stopPlaces = stopPlaceApi.fetchStopPlace(OSLO_S)
        println("Stop places at Oslo S:\n$stopPlaces")
    }
}
