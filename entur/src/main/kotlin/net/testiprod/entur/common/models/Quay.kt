package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.QuayQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.common.models.EstimatedCall.Companion.toDomain
import net.testiprod.entur.common.models.JourneyPattern.Companion.toDomain
import net.testiprod.entur.common.models.Line.Companion.toDomain

@Serializable
data class Quay(
    val id: String,
    val name: String,
    val publicCode: String?,
    val lines: List<Line>,
    val journeyPatterns: List<JourneyPattern>,
    val estimatedCalls: List<EstimatedCall>,
) : java.io.Serializable {
    companion object {
        internal fun StopPlaceDetailsQuery.Quay.toDomain(): Quay = Quay(
            id = id,
            name = name,
            publicCode = publicCode,
            lines = lines.map { it.toDomain() },
            journeyPatterns = journeyPatterns.mapNotNull { it?.toDomain() },
            estimatedCalls = emptyList(),
        )

        internal fun QuayQuery.Quay.toDomain(): Quay = Quay(
            id = id,
            name = name,
            publicCode = null,
            lines = emptyList(),
            journeyPatterns = emptyList(),
            estimatedCalls = estimatedCalls.map { it.estimatedCallFragment.toDomain() },
        )
    }
}
