package net.testiprod.entur.journeyplanner.trip.api

import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Trip
import kotlin.time.Clock
import kotlin.time.Instant

interface ITripApi {
    suspend fun fetchTrip(
        from: Location,
        to: Location,
        dateTime: Instant = Clock.System.now(),
        numTripPatterns: Int = 5,
        walkSpeed: Float = 1.3f,
        arriveBy: Boolean = false,
    ): Trip
}

