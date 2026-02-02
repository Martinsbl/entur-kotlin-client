package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable

@Serializable
data class ServiceJourney(
    val id: String,
    val directionType: DirectionType?,
    val line: Line,
    val situations: List<Situation>,
)
