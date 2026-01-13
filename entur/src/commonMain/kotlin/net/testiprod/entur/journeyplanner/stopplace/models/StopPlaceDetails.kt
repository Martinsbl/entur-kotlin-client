package net.testiprod.entur.journeyplanner.stopplace.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.common.models.EstimatedCall

@Serializable
data class StopPlaceDetails(
    val name: String,
    val id: String,
    val quays: List<Quay>?,
    val estimatedCalls: List<EstimatedCall>,
)
