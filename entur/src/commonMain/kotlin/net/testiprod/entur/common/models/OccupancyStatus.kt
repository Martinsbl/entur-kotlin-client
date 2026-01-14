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
}
