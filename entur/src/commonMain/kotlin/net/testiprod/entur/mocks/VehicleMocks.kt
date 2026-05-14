package net.testiprod.entur.mocks

import net.testiprod.entur.vehicle.models.Location
import net.testiprod.entur.vehicle.models.Vehicle
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

val mockVehicle = Vehicle(
    serviceJourneyId = "vehicle1",
    lineRef = "1",
    publicCode = "1",
    lineName = "1",
    lastUpdated = Clock.System.now().minus(10.seconds),
    delay = 120,
    destinationName = "Oslo S",
    bearing = 45.0,
    inCongestion = true,
    occupancyStatus = null,
    speed = 42.0,
    location = Location(59.91, 10.75),
)
