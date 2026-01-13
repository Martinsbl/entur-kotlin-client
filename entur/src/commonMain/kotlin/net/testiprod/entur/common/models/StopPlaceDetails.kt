package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceQuery
import net.testiprod.entur.common.models.EstimatedCall.Companion.toDomain
import net.testiprod.entur.common.models.Quay.Companion.toDomain

@Serializable
data class StopPlaceDetails(
    val name: String,
    val id: String,
    val quays: List<Quay>?,
    val estimatedCalls: List<EstimatedCall>,
) {
    companion object {
        internal fun StopPlaceDetailsQuery.StopPlace.toDomain(): StopPlaceDetails = StopPlaceDetails(
            id = id,
            name = name,
            quays = this.quays?.mapNotNull { it?.toDomain() },
            estimatedCalls = emptyList(),
        )

        internal fun StopPlaceQuery.StopPlace.toDomain(): StopPlaceDetails = StopPlaceDetails(
            name,
            id,
            emptyList(),
            estimatedCalls.map { it.estimatedCallFragment.toDomain() },
        )
    }
}
