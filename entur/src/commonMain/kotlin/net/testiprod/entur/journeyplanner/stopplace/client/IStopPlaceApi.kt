package net.testiprod.entur.journeyplanner.stopplace.client

import net.testiprod.entur.common.DEFAULT_NUMBER_OF_DEPARTURES
import net.testiprod.entur.common.models.DirectionType
import net.testiprod.entur.common.models.StopPlaceDetails
import net.testiprod.entur.common.models.StopPlaceQuay
import net.testiprod.entur.http.EnturResult

interface IStopPlaceApi {
    suspend fun fetchStopPlaceQuay(
        stopPlaceId: String,
        numberOfDepartures: Int = DEFAULT_NUMBER_OF_DEPARTURES,
        directionType: DirectionType? = null,
        whiteListedLines: List<String>? = null,
    ): EnturResult<StopPlaceQuay>

    suspend fun fetchQuay(
        quayId: String,
        numberOfDepartures: Int = DEFAULT_NUMBER_OF_DEPARTURES,
        directionType: DirectionType? = null,
        whiteListedLines: List<String>? = null,
    ): EnturResult<StopPlaceQuay>

    suspend fun fetchStopPlaceDetails(stopPlaceId: String): StopPlaceDetails
}
