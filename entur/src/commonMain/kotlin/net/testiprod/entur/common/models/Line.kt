package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Line(
    val id: String,
    val name: String?,
    val publicCode: String?,
    val presentation: Presentation?,
    val transportMode: TransportMode,
)
