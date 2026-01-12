package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.SituationsFragment
import net.testiprod.entur.common.StopMonitorUtils
import net.testiprod.entur.common.models.Situation.ReportType.Companion.toDomain
import net.testiprod.entur.common.serialization.DateSerializer
import java.util.Date
import java.util.Objects
import java.io.Serializable as JavaSerializable
import net.testiprod.entur.apollographql.journeyplanner.type.ReportType as EnturReportType

@Serializable
data class Situation(
    val id: String,
    val summary: String?,
    val description: String?,
    val advice: String?,
    val reportType: ReportType,
    @Serializable(DateSerializer::class)
    val startTime: Date?,
    @Serializable(DateSerializer::class)
    val endTime: Date?,
) : JavaSerializable {
    override fun equals(other: Any?): Boolean = (other is Situation) &&
        this.id == other.id

    override fun hashCode(): Int = Objects.hashCode(id)

    enum class ReportType(val rawValue: String) {
        UNKNOWN("unknown"),
        INCIDENT("incident"),
        GENERAL("general"),
        ;

        companion object {
            internal fun EnturReportType?.toDomain(): ReportType = when (this) {
                EnturReportType.general -> Situation.ReportType.GENERAL
                EnturReportType.incident -> Situation.ReportType.INCIDENT
                EnturReportType.UNKNOWN__ -> Situation.ReportType.UNKNOWN
                null -> Situation.ReportType.UNKNOWN
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
