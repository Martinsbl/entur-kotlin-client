package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.journeyplanner.trip.models.TransportSubMode

@Serializable
data class Line(
    val id: String,
    val name: String?,
    val publicCode: String?,
    val presentation: Presentation?,
    val situations: List<Situation>,
    val transportMode: TransportMode,
    val transportSubMode: TransportSubMode?,
)
