package net.testiprod.entur.common.models

import net.testiprod.entur.apollographql.journeyplanner.type.TransportMode as EnturTransportMode

enum class TransportMode(val rawValue: String) {
    AIR("air"),
    BUS("bus"),
    CABLEWAY("cableway"),
    COACH("coach"),
    FUNICULAR("funicular"),
    LIFT("lift"),
    METRO("metro"),
    MONORAIL("monorail"),
    RAIL("rail"),
    TAXI("taxi"),
    TRAM("tram"),
    TROLLEYBUS("trolleybus"),
    WATER("water"),
    UNKNOWN("unknown"),
}
