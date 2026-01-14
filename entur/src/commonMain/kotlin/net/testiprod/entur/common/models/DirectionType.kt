package net.testiprod.entur.common.models

enum class DirectionType(val rawValue: String) {
    UNKNOWN("unknown"),
    OUTBOUND("outbound"),
    INBOUND("inbound"),
    CLOCKWISE("clockwise"),
    ANTICLOCKWISE("anticlockwise"),
}
