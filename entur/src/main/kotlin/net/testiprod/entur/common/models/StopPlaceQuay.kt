package net.testiprod.entur.common.models

data class StopPlaceQuay(
    val id: String,
    val name: String,
    val estimatedCalls: List<EstimatedCall>,
)
