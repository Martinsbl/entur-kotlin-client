package net.testiprod.entur.journeyplanner.stopplace.models

import net.testiprod.entur.common.models.EstimatedCall

data class StopPlaceQuay(
    val id: String,
    val name: String,
    val estimatedCalls: List<EstimatedCall>,
)
