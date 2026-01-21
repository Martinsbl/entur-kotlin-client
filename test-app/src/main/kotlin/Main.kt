package net.testiprod

import kotlinx.coroutines.runBlocking
import net.testiprod.entur.common.OSLO_BUSSTERMINAL
import net.testiprod.entur.common.OSLO_S
import net.testiprod.entur.common.models.DirectionType
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.api.StopPlaceApi
import net.testiprod.entur.journeyplanner.stopplace.filtering.LineDirectionFilter
import net.testiprod.entur.journeyplanner.stopplace.filtering.LineFilter
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import net.testiprod.entur.journeyplanner.stopplace.service.StopPlaceService
import net.testiprod.entur.journeyplanner.trip.api.TripApi
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Trip
import net.testiprod.entur.vehicle.api.VehicleApi
import net.testiprod.entur.vehicle.models.Vehicle
import kotlin.time.Duration.Companion.seconds

fun main() {
    val stopPlaceApi = StopPlaceApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )
    val stopPlaceService = StopPlaceService(stopPlaceApi)
    val vehicleApi = VehicleApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )
    val tripApi = TripApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )

    runBlocking {
        testTripApi(tripApi)
//        testApi(stopPlaceApi, vehicleApi)
//        testService(stopPlaceService)
    }

    println("The End")
}

private suspend fun testTripApi(tripApi: TripApi) {
    val trips = tripApi.fetchTrip(
        Location.StopPlace("NSR:StopPlace:337"),
        Location.StopPlace("NSR:StopPlace:11"),
    )
    println(trips.toPrettyTrip())
}

private fun Trip.toPrettyTrip(): String {
    return """
        ${this.from.name} - ${this.to.name}
        Trip patterns:
        ${
        this.tripPatterns.joinToString(separator = "\n") { pattern ->
            " - starts at ${pattern.expectedStartTime}, duration: ${pattern.duration} seconds, lines: ${pattern.lines.joinToString {
                it.publicCode.toString()
            }}"
        }
    } 
    """.trimIndent()
}

private suspend fun testApi(
    stopPlaceApi: StopPlaceApi,
    vehicleApi: VehicleApi,
) {
    val stopPlaces = stopPlaceApi.fetchStopPlaceQuay(OSLO_S)
    println(stopPlaces.toPrettyStopPlace())
    val vehicles = vehicleApi.fetchVehicles("SKY", null)
    println(vehicles.toPrettyVehicles())
}

private suspend fun testService(stopPlaceService: StopPlaceService) {
    val filters = listOf(
        LineFilter("4", DirectionType.OUTBOUND),
        LineFilter("1", DirectionType.INBOUND),
    )
    stopPlaceService.observeStopPlace(
        OSLO_BUSSTERMINAL,
        numberOfDepartures = 20,
        refreshInterval = 30.seconds,
        filters = listOf(LineDirectionFilter(filters)),

    ).collect {
        println(it.toPrettyStopPlace())
    }
}

private fun EnturResult<StopPlaceQuay>.toPrettyStopPlace(): String {
    when (this) {
        is EnturResult.Success -> {
            val departures = data.estimatedCalls.joinToString(separator = "\n") { call ->
                "to ${call.serviceJourney?.line?.publicCode} ${call.destinationDisplay.frontText} at ${call.expectedDepartureTime}"
            }
            return "Stop Place: ${data.name} (ID: ${data.id})\nDepartures:\n$departures"
        }

        is EnturResult.Error -> {
            return "Error fetching stop places: $this"
        }
    }
}

private fun EnturResult<List<Vehicle>>.toPrettyVehicles(): String {
    when (this) {
        is EnturResult.Success -> {
            if (data.isEmpty()) {
                return "No vehicles found."
            }
            val vehiclesInfo = data.joinToString(separator = "\n") { vehicle ->
                "Vehicle ${vehicle.serviceJourneyId} on line ${vehicle.lineRef} at position (${vehicle.location.lat}, ${vehicle.location.lon})"
            }
            return "Vehicles:\n$vehiclesInfo"
        }

        is EnturResult.Error -> {
            return "Error fetching vehicles: $this"
        }
    }
}
