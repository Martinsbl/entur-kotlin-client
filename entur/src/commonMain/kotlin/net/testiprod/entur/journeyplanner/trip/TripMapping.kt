package net.testiprod.entur.journeyplanner.trip

import com.apollographql.apollo.api.Optional
import net.testiprod.entur.apollographql.journeyplanner.TripQuery
import net.testiprod.entur.apollographql.journeyplanner.type.InputCoordinates
import net.testiprod.entur.common.toDomain
import net.testiprod.entur.journeyplanner.trip.models.Leg
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Mode
import net.testiprod.entur.journeyplanner.trip.models.Place
import net.testiprod.entur.journeyplanner.trip.models.Trip
import kotlin.time.Instant
import net.testiprod.entur.apollographql.journeyplanner.TripQuery.Trip as EnturTrip
import net.testiprod.entur.apollographql.journeyplanner.TripQuery.TripPattern as EnturTripPattern

internal fun Location.toEnturLocation(): net.testiprod.entur.apollographql.journeyplanner.type.Location {
    return when (this) {
        is Location.StopPlace -> net.testiprod.entur.apollographql.journeyplanner.type.Location(
            place = Optional.present(this.id),
        )

        is Location.Coordinate -> {
            net.testiprod.entur.apollographql.journeyplanner.type.Location(
                name = Optional.present(this.name),
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
    expectedStartTime = Instant.parse(this.expectedStartTime.toString()),
    duration = this.duration.toString().toInt(),
    streetDistance = this.streetDistance,
    legs = this.legs.map { it.toDomain() },
)

internal fun TripQuery.Leg.toDomain() = Leg(
    mode = this.mode.toDomain(),
    distance = this.distance,
    line = this.line?.linesFragment?.toDomain(),
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

internal fun net.testiprod.entur.apollographql.journeyplanner.type.Mode.toDomain(): Mode {
    return Mode.entries.firstOrNull {
        it.name.equals(this.name, ignoreCase = true)
    } ?: Mode.UNKNOWN
}
