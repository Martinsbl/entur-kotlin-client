package net.testiprod.entur.journeyplanner.trip.models

import net.testiprod.entur.common.models.Line

data class Trip(
    val from: Place,
    val to: Place,
    val tripPatterns: List<TripPattern>,
)

data class Place(val name: String?)

data class TripPattern(
    val expectedStartTime: String,
    val duration: Int?,
    val streetDistance: Double?,
    // TODO Legs
    val lines: List<Line>,
)

sealed class Location {
    data class StopPlace(val id: String) : Location()
    data class Coordinate(
        val latitude: Double,
        val longitude: Double,
    ) : Location()
}
