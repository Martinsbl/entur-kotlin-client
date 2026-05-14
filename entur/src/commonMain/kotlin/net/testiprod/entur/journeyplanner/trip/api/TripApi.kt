package net.testiprod.entur.journeyplanner.trip.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import net.testiprod.entur.apollographql.journeyplanner.TripQuery
import net.testiprod.entur.http.EnturApolloClientFactory
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Trip
import net.testiprod.entur.journeyplanner.trip.toDomain
import net.testiprod.entur.journeyplanner.trip.toEnturLocation
import kotlin.time.Instant

class TripApi(private val apolloClient: ApolloClient) : ITripApi {

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
            dateTime = Optional.present(dateTime.toString()),
            numTripPatterns = Optional.present(numTripPatterns),
            walkSpeed = Optional.present(walkSpeed.toDouble()),
            arriveBy = Optional.present(arriveBy),
        )
        val response = apolloClient.query(query).execute()
        response.exception?.let { throw it }
        response.errors?.let {
            throw Exception(
                "Error fetching trip from '$from' to '$to'. Errors=${it.joinToString()}",
            )
        }
        return response.data?.trip?.toDomain() ?: throw Exception("Got neither data, nor errors from Entur.")
    }

    companion object {
        operator fun invoke(block: TripApiConfig.() -> Unit): TripApi {
            val config = TripApiConfig().apply(block)
            require(config.enturClientName.isNotBlank()) {
                "User agent must be provided for VehicleApi"
            }
            return TripApi(
                EnturApolloClientFactory.create(
                    enturClientName = config.enturClientName,
                    baseUrl = config.baseUrl,
                    logLevel = config.logLevel,
                ),
            )
        }
    }
}
