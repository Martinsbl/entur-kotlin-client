package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceQuery
import net.testiprod.entur.common.models.EstimatedCall.Companion.toDomain
import net.testiprod.entur.common.models.Quay.Companion.toDomain
import java.io.Serializable as JavaSerializable

@Serializable
data class StopPlace(
    val name: String,
    val id: String,
    val quays: List<Quay>?,
    val estimatedCalls: List<EstimatedCall>,
) : JavaSerializable {
    companion object {
        internal fun StopPlaceDetailsQuery.StopPlace.toDomain(): StopPlace = StopPlace(
            id = id,
            name = name,
            quays = this.quays?.mapNotNull { it?.toDomain() },
            estimatedCalls = emptyList(),
        )

        internal fun StopPlaceQuery.StopPlace.toDomain(): StopPlace = StopPlace(
            name,
            id,
            emptyList(),
            estimatedCalls.map { it.estimatedCallFragment.toDomain() },
        )
    }
}
