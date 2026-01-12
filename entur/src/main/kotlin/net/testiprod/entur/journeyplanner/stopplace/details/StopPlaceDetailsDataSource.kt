package net.testiprod.entur.journeyplanner.stopplace.details

import com.apollographql.apollo.ApolloClient
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.common.exceptions.EnturResponseException
import net.testiprod.entur.common.models.StopPlace
import net.testiprod.entur.common.models.StopPlace.Companion.toDomain

class StopPlaceDetailsDataSource(private val apolloClient: ApolloClient) {

    suspend fun fetchStopPlace(stopPlaceId: String): StopPlace {
        val response =
            apolloClient
                .query(
                    StopPlaceDetailsQuery(stopPlaceId),
                ).execute()

        response.errors?.let {
            throw EnturResponseException(
                "Error fetching data for $stopPlaceId. Errors=${it.joinToString()}",
                null,
            )
        }
        response.data?.stopPlace?.let {
            val stopPlace = it.toDomain()
            return stopPlace
        }
        throw EnturResponseException("Got neither data, nor errors from Entur.", null)
    }
}
