package net.testiprod.entur.journeyplanner.trip.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import net.testiprod.entur.apollographql.journeyplanner.TripQuery
import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.http.EnturApolloClientFactory
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Trip
import net.testiprod.entur.journeyplanner.trip.toDomain
import net.testiprod.entur.journeyplanner.trip.toEnturLocation
import kotlin.time.Instant

class TripApi(private val apolloClient: ApolloClient) : ITripApi {

    constructor(
        companyName: String,
        appName: String,
        serverUrl: String = JOURNEY_PLANNER_BASE_URL,
    ) : this(
        EnturApolloClientFactory.create(companyName, appName, serverUrl),
    )

    override suspend fun fetchTrip(
        from: Location,
        to: Location,
        dateTime: Instant,
        numTripPatterns: Int,
        walkSpeed: Float,
        arriveBy: Boolean,
    ): Trip {
        val query = TripQuery(
            from = from.toEnturLocation(),
            to = to.toEnturLocation(),
            dateTime = Optional.Companion.present(dateTime.toString()),
            numTripPatterns = Optional.Companion.present(numTripPatterns),
            walkSpeed = Optional.Companion.present(walkSpeed.toDouble()),
            arriveBy = Optional.Companion.present(arriveBy),
        )
        val response = apolloClient.query(query).execute()
        response.errors?.let {
            throw Exception(
                "Error fetching trip from '$from' to '$to'. Errors=${it.joinToString()}",
            )
        }
        return response.data?.trip?.toDomain() ?: throw Exception("Got neither data, nor errors from Entur.")
    }
}
