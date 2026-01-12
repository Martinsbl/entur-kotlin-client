package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.common.models.DirectionType.Companion.toDomain
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery.JourneyPattern as EnturJourneyPattern

@Serializable
data class JourneyPattern(val directionType: DirectionType?) {
    companion object {
        internal fun EnturJourneyPattern.toDomain(): JourneyPattern = JourneyPattern(
            directionType = directionType?.toDomain(),
        )
    }
}
