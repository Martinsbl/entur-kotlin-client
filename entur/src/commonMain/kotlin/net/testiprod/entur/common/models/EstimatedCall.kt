package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.EstimatedCallFragment
import net.testiprod.entur.common.StopMonitorUtils
import net.testiprod.entur.common.models.OccupancyStatus.Companion.toDomain
import net.testiprod.entur.common.models.ServiceJourney.Companion.toDomain
import net.testiprod.entur.common.models.Situation.Companion.toDomain
import net.testiprod.entur.common.serialization.InstantSerializer
import kotlin.time.Instant

@Serializable
data class EstimatedCall(
    val destinationDisplay: DestinationDisplay,
    @Serializable(with = InstantSerializer::class)
    val expectedDepartureTime: Instant,
    @Serializable(with = InstantSerializer::class)
    val aimedDepartureTime: Instant,
    val realTime: Boolean,
    val occupancyStatus: OccupancyStatus?,
    val serviceJourney: ServiceJourney?,
    val situations: List<Situation>,
) {
    companion object {
        internal fun EstimatedCallFragment.toDomain(): EstimatedCall = EstimatedCall(
            DestinationDisplay(destinationDisplay?.frontText),
            StopMonitorUtils.parseDate(expectedDepartureTime as String),
            StopMonitorUtils.parseDate(aimedDepartureTime as String),
            realtime,
            occupancyStatus.toDomain(),
            serviceJourney.toDomain(),
            getAllSituations(),
        )

        private fun EstimatedCallFragment.getAllSituations(): List<Situation> {
            val situations = situations.map { it.situationsFragment.toDomain() }.toMutableList()
            situations.addAll(serviceJourney.situations.map { it.situationsFragment.toDomain() })
            situations.addAll(serviceJourney.line.situations.map { it.situationsFragment.toDomain() })
            return situations.distinct()
        }
    }
}
