package net.testiprod.entur.journeyplanner.trip

import com.apollographql.apollo.api.Optional
import net.testiprod.entur.apollographql.journeyplanner.TripQuery
import net.testiprod.entur.apollographql.journeyplanner.type.InputCoordinates
import net.testiprod.entur.common.toDomain
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Place
import net.testiprod.entur.journeyplanner.trip.models.Trip
import net.testiprod.entur.apollographql.journeyplanner.TripQuery.Trip as EnturTrip
import net.testiprod.entur.apollographql.journeyplanner.TripQuery.TripPattern as EnturTripPattern

internal fun Location.toEnturLocation(): net.testiprod.entur.apollographql.journeyplanner.type.Location {
    return when (this) {
        is Location.StopPlace -> net.testiprod.entur.apollographql.journeyplanner.type.Location(
            place = Optional.present(this.id),
        )

        is Location.Coordinate -> {
            net.testiprod.entur.apollographql.journeyplanner.type.Location(
                coordinates = Optional.present(
                    InputCoordinates(
                        latitude = this.latitude,
                        longitude = this.longitude,
                    ),
                ),
            )
        }
    }
}

internal fun EnturTrip.toDomain() = Trip(
    to = this.toPlace.toDomain(),
    from = this.fromPlace.toDomain(),
    tripPatterns = this.tripPatterns.map { it.toDomain() },
)

internal fun EnturTripPattern.toDomain() = net.testiprod.entur.journeyplanner.trip.models.TripPattern(
    expectedStartTime = this.expectedStartTime.toString(),
    duration = this.duration.toString().toInt(),
    streetDistance = this.streetDistance,
    // TODO Legs
    lines = this.legs.mapNotNull { it.line?.linesFragment?.toDomain() },
)

internal fun TripQuery.ToPlace.toDomain(): Place {
    return Place(
        name = this.name,
    )
}

internal fun TripQuery.FromPlace.toDomain(): Place {
    return Place(
        name = this.name,
    )
}
