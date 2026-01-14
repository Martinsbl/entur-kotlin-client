package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.common.serialization.InstantSerializer
import kotlin.time.Instant

@Serializable
data class Situation(
    val id: String,
    val summary: String?,
    val description: String?,
    val advice: String?,
    val reportType: ReportType,
    @Serializable(with = InstantSerializer::class)
    val startTime: Instant?,
    @Serializable(with = InstantSerializer::class)
    val endTime: Instant?,
) {
    override fun equals(other: Any?): Boolean = (other is Situation) &&
        this.id == other.id

    override fun hashCode(): Int {
        return id.hashCode()
    }

    enum class ReportType(val rawValue: String) {
        UNKNOWN("unknown"),
        INCIDENT("incident"),
        GENERAL("general"),
    }
}
