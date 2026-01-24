package net.testiprod.entur.journeyplanner.trip.models

import net.testiprod.entur.common.models.Line
import kotlin.time.Duration
import kotlin.time.Instant

data class Trip(
    val from: Place,
    val to: Place,
    val tripPatterns: List<TripPattern>,
)

data class Place(
    val name: String?,
    val latitude: Double,
    val longitude: Double,
    val quay: Quay?,
)

data class Quay(
    val name: String,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val publicCode: String?,
)

data class TripPattern(
    val aimedEndTime: Instant,
    val aimedStartTime: Instant,
    val duration: Duration?,
    val expectedEndTime: Instant,
    val expectedStartTime: Instant,
    val streetDistance: Double?,
    val waitingTime: Duration?,
    val walkTime: Duration?,
    val legs: List<Leg>,
)

data class Leg(
    val authority: Authority?,
    val distance: Double?,
    val duration: Duration,
    val fromPlace: Place,
    val line: Line?,
    val mode: Mode,
    val operator: Operator?,
//    val pointsOnLink: PointsOnLink, TODO
    val realTime: Boolean,
    val ride: Boolean,
    val toPlace: Place,
    val transportSubMode: TransportSubMode?,
)

data class Authority(val name: String)

data class Operator(val name: String)

sealed class Location {

    data class StopPlace(val id: String) : Location()

    data class Coordinate(
        val name: String,
        val latitude: Double,
        val longitude: Double,
    ) : Location()
}
