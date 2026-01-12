package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.Lines
import net.testiprod.entur.common.StopMonitorUtils.toColorInt
import java.io.Serializable as JavaSerializable
import net.testiprod.entur.apollographql.journeyplanner.fragment.EstimatedCallFragment.Presentation as EnturPresentation

@Serializable
data class Presentation(
    val backgroundColor: Int?,
    val textColor: Int?,
) : JavaSerializable {
    companion object {
        internal fun Lines.Presentation.toDomain(): Presentation = Presentation(
            backgroundColor = this.colour.toColorInt(),
            textColor = this.textColour.toColorInt(),
        )

        internal fun EnturPresentation.toDomain(): Presentation = Presentation(
            backgroundColor = this.colour.toColorInt(),
            textColor = this.textColour.toColorInt(),
        )
    }
}
