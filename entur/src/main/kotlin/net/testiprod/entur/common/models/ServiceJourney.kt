package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.common.models.DirectionType.Companion.toDomain
import net.testiprod.entur.common.models.Line.Companion.toDomain
import java.io.Serializable as JavaSerializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.EstimatedCallFragment.ServiceJourney as EnturServiceJourney

@Serializable
data class ServiceJourney(
    val id: String,
    val directionType: DirectionType?,
    val line: Line,
) : JavaSerializable {
    companion object {
        internal fun EnturServiceJourney.toDomain(): ServiceJourney = ServiceJourney(
            id,
            directionType?.toDomain(),
            line.toDomain(),
        )
    }
}
