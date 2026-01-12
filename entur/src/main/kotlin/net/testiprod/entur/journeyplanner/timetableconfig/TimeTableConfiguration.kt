package net.testiprod.entur.journeyplanner.timetableconfig

import net.testiprod.entur.common.DEFAULT_NUMBER_OF_DEPARTURES
import net.testiprod.entur.common.models.DirectionType
import java.io.Serializable

data class TimeTableConfiguration(
    val stopId: String,
    val refreshRateMs: Long = 30_000L,
    val whitelistedLines: List<String>? = null,
    val directionType: DirectionType? = null,
    val numberOfDepartures: Int = DEFAULT_NUMBER_OF_DEPARTURES,
) : Serializable {

    val isQuay: Boolean = stopId.split(":")[1] == "Quay"
}
