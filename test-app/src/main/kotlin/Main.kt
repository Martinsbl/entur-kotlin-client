package net.testiprod

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import net.testiprod.entur.common.DRAMMEN_BUSS_STASJON
import net.testiprod.entur.common.models.Line
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.api.StopPlaceApi
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import net.testiprod.entur.journeyplanner.stopplace.service.StopPlaceService
import net.testiprod.entur.journeyplanner.trip.api.TripApi
import net.testiprod.entur.journeyplanner.trip.models.Leg
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Trip
import net.testiprod.entur.vehicle.api.VehicleApi
import net.testiprod.entur.vehicle.models.Vehicle
import net.testiprod.entur.vehicle.service.VehicleService
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
    val vehicleService = VehicleService(vehicleApi, 30_000)
    val tripApi = TripApi(
        "github.com/martinsbl",
        "kotlin-entur-client",
    )

    runBlocking {
//        testTripApi(tripApi)
        val enturResult = stopPlaceApi.fetchStopPlaceQuay(DRAMMEN_BUSS_STASJON)
        require(enturResult is EnturResult.Success)
        val estimatedCall = enturResult.data.estimatedCalls.first()
        vehicleService.getVehicleFlow(
            estimatedCall.serviceJourney!!.id,
        ).collect { it ->
            println(it.joinToString { it.toPrettyPrintVehicle() })
        }
        delay(60.seconds)
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
            " - starts at ${pattern.expectedStartTime}, duration: ${pattern.duration}, legs: ${
                pattern.legs.joinToString { it.toPrettyLeg() }
            }"
        }
    } 
    """.trimIndent()
}

private fun Leg.toPrettyLeg(): String {
    return "${this.serviceJourney?.line?.toPrettyLine() ?: "walk"} ${this.distance} meters (${this.duration})"
}

private fun Line.toPrettyLine(): String {
    return "${this.transportMode.name.lowercase().capitalize()} ${this.publicCode}"
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
                vehicle.toPrettyPrintVehicle()
            }
            return "Vehicles:\n$vehiclesInfo"
        }

        is EnturResult.Error -> {
            return "Error fetching vehicles: $this"
        }
    }
}

private fun Vehicle.toPrettyPrintVehicle(): String =
    "Vehicle $serviceJourneyId on line $lineRef at position (${location.lat}, ${location.lon}), ${this.bearing} degrees, speed ${this.speed} m/s, occupancy ${this.occupancyStatus}, ${this.lastUpdated}"
