package net.testiprod.entur.journeyplanner.stopplace.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.common.models.EstimatedCall
import net.testiprod.entur.common.models.JourneyPattern
import net.testiprod.entur.common.models.Line

@Serializable
data class Quay(
    val id: String,
    val name: String,
    val publicCode: String?,
    val lines: List<Line>,
    val journeyPatterns: List<JourneyPattern>,
    val estimatedCalls: List<EstimatedCall>,
)
