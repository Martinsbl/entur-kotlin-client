package net.testiprod.entur.common.models

enum class OccupancyStatus(
    val rawValue: String,
    val text: String,
) {
    EMPTY("empty", "Veldig god plass"),
    MANY_SEATS_AVAILABLE("manySeatsAvailable", "God plass"),
    FEW_SEATS_AVAILABLE("fewSeatsAvailable", "Få ledige seter"),
    STANDING_ROOM_ONLY("standingRoomOnly", "Kun ståplasser"),
    CRUSHED_STANDING_ROOM_ONLY("crushedStandingRoomOnly", "Få ledige ståplasser"),
    FULL("full", "Fullt"),
    NOT_ACCEPTING_PASSENGERS("notAcceptingPassengers", "Ingen påstigning"),
    UNKNOWN("UNKNOWN__", "Ukjent"),
    ;

    companion object {
        internal fun net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus?.toDomain(): OccupancyStatus? =
            when (this) {
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.crushedStandingRoomOnly -> CRUSHED_STANDING_ROOM_ONLY
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.empty -> EMPTY
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.fewSeatsAvailable -> FEW_SEATS_AVAILABLE
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.full -> FULL
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.manySeatsAvailable -> MANY_SEATS_AVAILABLE
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.noData -> null
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.notAcceptingPassengers -> NOT_ACCEPTING_PASSENGERS
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.standingRoomOnly -> STANDING_ROOM_ONLY
                net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.UNKNOWN__ -> UNKNOWN
                null -> UNKNOWN
            }
    }
}
