package net.testiprod.entur.journeyplanner.trip.models

import net.testiprod.entur.common.models.Line
import kotlin.time.Instant

data class Trip(
    val from: Place,
    val to: Place,
    val tripPatterns: List<TripPattern>,
)

data class Place(val name: String?)

data class TripPattern(
    val expectedStartTime: Instant,
    val duration: Int?,
    val streetDistance: Double?,
    val legs: List<Leg>,
)

data class Leg(
    val mode: Mode,
    val distance: Double?,
    val line: Line?,
)

sealed class Location {

    data class StopPlace(val id: String) : Location()

    data class Coordinate(
        val name: String,
        val latitude: Double,
        val longitude: Double,
    ) : Location()
}
