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
    ;

    companion object {

        internal fun EnturTransportMode?.toDomain(): TransportMode {
            return when (this) {
                EnturTransportMode.air -> AIR
                EnturTransportMode.bus -> BUS
                EnturTransportMode.cableway -> CABLEWAY
                EnturTransportMode.water -> WATER
                EnturTransportMode.funicular -> FUNICULAR
                EnturTransportMode.lift -> LIFT
                EnturTransportMode.rail -> RAIL
                EnturTransportMode.metro -> METRO
                EnturTransportMode.taxi -> TAXI
                EnturTransportMode.tram -> TRAM
                EnturTransportMode.trolleybus -> TROLLEYBUS
                EnturTransportMode.monorail -> MONORAIL
                EnturTransportMode.coach -> COACH
                EnturTransportMode.unknown -> UNKNOWN
                EnturTransportMode.UNKNOWN__ -> UNKNOWN
                null -> UNKNOWN
            }
        }
    }
}
