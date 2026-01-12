package net.testiprod.entur.common.models

import net.testiprod.entur.apollographql.journeyplanner.type.DirectionType as EnturDirectionType

enum class DirectionType(val rawValue: String) {
    UNKNOWN("unknown"),
    OUTBOUND("outbound"),
    INBOUND("inbound"),
    CLOCKWISE("clockwise"),
    ANTICLOCKWISE("anticlockwise"),
    ;

    companion object {
        internal fun EnturDirectionType.toDomain(): DirectionType = when (this) {
            EnturDirectionType.unknown -> UNKNOWN
            EnturDirectionType.outbound -> OUTBOUND
            EnturDirectionType.inbound -> INBOUND
            EnturDirectionType.clockwise -> CLOCKWISE
            EnturDirectionType.anticlockwise -> ANTICLOCKWISE
            EnturDirectionType.UNKNOWN__ -> UNKNOWN
        }
    }
}
