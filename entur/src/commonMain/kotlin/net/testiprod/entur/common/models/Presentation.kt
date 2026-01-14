package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable

@Serializable
data class Presentation(
    val backgroundColor: Int?,
    val textColor: Int?,
)
