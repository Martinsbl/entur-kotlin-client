package net.testiprod.entur.journeyplanner.stopplace.filtering

import net.testiprod.entur.common.models.DirectionType

data class LineFilter(
    val publicCode: String,
    val direction: DirectionType? = null,
)
