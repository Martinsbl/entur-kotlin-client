package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import java.io.Serializable as JavaSerializable

@Serializable
data class DestinationDisplay(val frontText: String?) : JavaSerializable
