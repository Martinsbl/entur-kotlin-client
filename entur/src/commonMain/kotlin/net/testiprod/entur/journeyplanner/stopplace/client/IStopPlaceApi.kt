package net.testiprod.entur.journeyplanner.stopplace.client

import net.testiprod.entur.common.DEFAULT_NUMBER_OF_DEPARTURES
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.filtering.EstimatedCallFilter
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceDetails
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay

interface IStopPlaceApi {
    suspend fun fetchStopPlaceQuay(
        stopPlaceId: String,
        numberOfDepartures: Int = DEFAULT_NUMBER_OF_DEPARTURES,
        whiteListedLines: List<String>? = null,
        filters: List<EstimatedCallFilter>? = null,
    ): EnturResult<StopPlaceQuay>

    suspend fun fetchQuay(
        quayId: String,
        numberOfDepartures: Int = DEFAULT_NUMBER_OF_DEPARTURES,
        whiteListedLines: List<String>? = null,
        filters: List<EstimatedCallFilter>? = null,
    ): EnturResult<StopPlaceQuay>

    suspend fun fetchStopPlaceDetails(stopPlaceId: String): StopPlaceDetails
}
