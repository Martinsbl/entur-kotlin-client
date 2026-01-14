package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.common.serialization.InstantSerializer
import kotlin.time.Instant

@Serializable
data class EstimatedCall(
    val destinationDisplay: DestinationDisplay,
    @Serializable(with = InstantSerializer::class)
    val expectedDepartureTime: Instant,
    @Serializable(with = InstantSerializer::class)
    val aimedDepartureTime: Instant,
    val realTime: Boolean,
    val occupancyStatus: OccupancyStatus?,
    val serviceJourney: ServiceJourney?,
    val situations: List<Situation>,
)
