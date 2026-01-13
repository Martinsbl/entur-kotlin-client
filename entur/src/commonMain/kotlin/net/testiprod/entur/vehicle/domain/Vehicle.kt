package net.testiprod.entur.vehicle.domain

import net.testiprod.entur.common.models.OccupancyStatus
import kotlin.time.Instant

data class Vehicle(
    val serviceJourneyId: String,
    val lineRef: String?,
    val publicCode: String?,
    val lineName: String?,
    val lastUpdated: Instant,
    val delay: Int?,
    val destinationName: String?,
    val bearing: Double?,
    val inCongestion: Boolean?,
    val occupancyStatus: OccupancyStatus?,
    val speed: Double?,
    val location: Location,
)
