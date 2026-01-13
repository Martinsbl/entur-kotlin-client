package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.SituationsFragment
import net.testiprod.entur.common.StopMonitorUtils
import net.testiprod.entur.common.models.Situation.ReportType.Companion.toDomain
import net.testiprod.entur.common.serialization.InstantSerializer
import kotlin.time.Instant
import net.testiprod.entur.apollographql.journeyplanner.type.ReportType as EnturReportType

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
        ;

        companion object {
            internal fun EnturReportType?.toDomain(): ReportType = when (this) {
                EnturReportType.general -> GENERAL
                EnturReportType.incident -> INCIDENT
                EnturReportType.UNKNOWN__ -> UNKNOWN
                null -> UNKNOWN
            }
        }
    }

    companion object {
        internal fun SituationsFragment.toDomain(): Situation = Situation(
            id,
            summary.firstOrNull()?.value,
            description.firstOrNull()?.value,
            advice.firstOrNull()?.value,
            reportType.toDomain(),
            validityPeriod?.startTime?.let { StopMonitorUtils.parseDate(it as String) },
            validityPeriod?.endTime?.let { StopMonitorUtils.parseDate(it as String) },
        )
    }
}
