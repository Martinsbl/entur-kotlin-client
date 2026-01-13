package net.testiprod.entur.journeyplanner.stopplace.client

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import net.testiprod.entur.apollographql.journeyplanner.QuayQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceQuery
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.common.exceptions.EnturResponseException
import net.testiprod.entur.common.models.DirectionType
import net.testiprod.entur.common.models.EstimatedCall
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceDetails
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import net.testiprod.entur.http.EnturApolloClientFactory
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.toDomain

class StopPlaceApi(private val apolloClient: ApolloClient) : IStopPlaceApi {
    constructor(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
    ) : this(
        EnturApolloClientFactory.create(companyName, appName, serverUrl),
    )

    override suspend fun fetchStopPlaceQuay(
        stopPlaceId: String,
        numberOfDepartures: Int,
        directionType: DirectionType?,
        whiteListedLines: List<String>?,
    ): EnturResult<StopPlaceQuay> {
        val query = StopPlaceQuery(
            stopPlaceId,
            Optional.presentIfNotNull(numberOfDepartures),
            Optional.presentIfNotNull(whiteListedLines),
        )
        val response = apolloClient.query(query).execute()

        response.errors?.let {
            throw EnturResponseException(
                "Error fetching data for stop place '$stopPlaceId'. Errors=${it.joinToString()}",
                null,
            )
        }
        response.data?.stopPlace?.let {
            val stopPlace = it.toDomain()
            val filteredEstimatedCalls = filterResponse(stopPlace.estimatedCalls, directionType)
            val filteredStopPlace =
                StopPlaceQuay(
                    stopPlace.id,
                    stopPlace.name,
                    filteredEstimatedCalls,
                )
            return EnturResult.Success(filteredStopPlace)
        }
        throw EnturResponseException("Got neither data, nor errors from Entur.", null)
    }

    override suspend fun fetchQuay(
        quayId: String,
        numberOfDepartures: Int,
        directionType: DirectionType?,
        whiteListedLines: List<String>?,
    ): EnturResult<StopPlaceQuay> {
        val query = QuayQuery(
            quayId,
            Optional.presentIfNotNull(numberOfDepartures),
            Optional.presentIfNotNull(whiteListedLines),
        )
        val response = apolloClient.query(query).execute()

        response.errors?.let {
            throw EnturResponseException(
                "Error fetching data for quay '$quayId'. Errors=${it.joinToString()}",
                null,
            )
        }
        response.data?.quay?.let {
            val quay = it.toDomain()
            val filteredEstimatedCalls = filterResponse(quay.estimatedCalls, directionType)
            val filteredStopPlace = StopPlaceQuay(
                quay.id,
                quay.name,
                filteredEstimatedCalls,
            )
            return EnturResult.Success(filteredStopPlace)
        }
        throw EnturResponseException("Got neither data, nor errors from Entur.", null)
    }

    override suspend fun fetchStopPlaceDetails(stopPlaceId: String): StopPlaceDetails {
        val response = apolloClient.query(
            StopPlaceDetailsQuery(stopPlaceId),
        ).execute()

        response.errors?.let {
            throw EnturResponseException(
                "Error fetching details for stop $stopPlaceId. Errors=${it.joinToString()}",
                null,
            )
        }
        response.data?.stopPlace?.let {
            val stopPlace = it.toDomain()
            return stopPlace
        }
        throw EnturResponseException("Got neither data, nor errors from Entur.", null)
    }

    private fun filterResponse(
        estimatedCalls: List<EstimatedCall>,
        directionType: DirectionType?,
    ): List<EstimatedCall> = estimatedCalls
        .filter { filterDirection(it, directionType) }
        .sortedBy { it.expectedDepartureTime }

    private fun filterDirection(
        estimatedCall: EstimatedCall,
        directionType: DirectionType?,
    ): Boolean {
        if (directionType == null) {
            return true // Return all lines when no filters
        }
        return directionType == estimatedCall.serviceJourney?.directionType
    }
}
