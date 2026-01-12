package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.common.models.Presentation.Companion.toDomain
import net.testiprod.entur.common.models.TransportMode.Companion.toDomain
import java.io.Serializable as JavaSerializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.EstimatedCallFragment.Line as EnturLine

@Serializable
data class Line(
    val id: String,
    val name: String?,
    val publicCode: String?,
    val presentation: Presentation?,
    val transportMode: TransportMode,
) : JavaSerializable {
    companion object {
        internal fun StopPlaceDetailsQuery.Line.toDomain(): Line = Line(
            id = lines.id,
            name = lines.name,
            publicCode = lines.publicCode,
            presentation = lines.presentation?.toDomain(),
            transportMode = lines.transportMode.toDomain(),
        )

        internal fun EnturLine.toDomain(): Line = Line(
            id = id,
            name = name,
            publicCode = publicCode,
            presentation = presentation?.toDomain(),
            transportMode = transportMode.toDomain(),
        )
    }
}
