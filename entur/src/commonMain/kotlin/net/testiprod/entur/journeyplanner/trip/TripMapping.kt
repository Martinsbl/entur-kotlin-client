package net.testiprod.entur.journeyplanner.trip

import com.apollographql.apollo.api.Optional
import net.testiprod.entur.apollographql.journeyplanner.TripQuery
import net.testiprod.entur.apollographql.journeyplanner.fragment.PlaceFragment
import net.testiprod.entur.apollographql.journeyplanner.type.InputCoordinates
import net.testiprod.entur.common.toDomain
import net.testiprod.entur.journeyplanner.trip.models.Authority
import net.testiprod.entur.journeyplanner.trip.models.Leg
import net.testiprod.entur.journeyplanner.trip.models.Location
import net.testiprod.entur.journeyplanner.trip.models.Mode
import net.testiprod.entur.journeyplanner.trip.models.Operator
import net.testiprod.entur.journeyplanner.trip.models.Place
import net.testiprod.entur.journeyplanner.trip.models.Quay
import net.testiprod.entur.journeyplanner.trip.models.TransportSubMode
import net.testiprod.entur.journeyplanner.trip.models.Trip
import kotlin.time.Duration.Companion.seconds
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
    to = this.toPlace.placeFragment.toDomain(),
    from = this.fromPlace.placeFragment.toDomain(),
    tripPatterns = this.tripPatterns.map { it.toDomain() },
)

internal fun EnturTripPattern.toDomain() = net.testiprod.entur.journeyplanner.trip.models.TripPattern(
    aimedEndTime = Instant.parse(this.aimedEndTime.toString()),
    aimedStartTime = Instant.parse(this.aimedStartTime.toString()),
    duration = (this.duration as Int?)?.seconds,
    expectedEndTime = Instant.parse(this.expectedEndTime.toString()),
    expectedStartTime = Instant.parse(this.expectedStartTime.toString()),
    streetDistance = this.streetDistance,
    legs = this.legs.map { it.toDomain() },
    waitingTime = (this.waitingTime as Int?)?.seconds,
    walkTime = (this.walkTime as Int?)?.seconds,
)

internal fun TripQuery.Leg.toDomain() = Leg(
    aimedEndTime = Instant.parse(this.aimedEndTime.toString()),
    aimedStartTime = Instant.parse(this.aimedStartTime.toString()),
    authority = this.authority?.let { Authority(it.name) },
    distance = this.distance,
    duration = (this.duration as Int).seconds,
    expectedEndTime = Instant.parse(this.expectedEndTime.toString()),
    expectedStartTime = Instant.parse(this.expectedStartTime.toString()),
    fromPlace = this.fromPlace.placeFragment.toDomain(),
    line = this.line?.linesFragment?.toDomain(),
    mode = this.mode.toDomain(),
    operator = this.operator?.let { Operator(it.name) },
    realTime = this.realtime,
    ride = this.ride,
    toPlace = this.toPlace.placeFragment.toDomain(),
    transportSubMode = this.transportSubmode?.let { TransportSubMode.fromValue(it.rawValue) },
)

internal fun PlaceFragment.toDomain() = Place(
    name = this.name,
    latitude = this.latitude,
    longitude = this.longitude,
    quay = this.quay?.toDomain(),
)

internal fun PlaceFragment.Quay.toDomain(): Quay {
    return Quay(
        name = this.quayFragment.name,
        description = this.quayFragment.description,
        latitude = this.quayFragment.latitude,
        longitude = this.quayFragment.longitude,
        publicCode = this.quayFragment.publicCode,
    )
}

internal fun net.testiprod.entur.apollographql.journeyplanner.type.Mode.toDomain(): Mode {
    return Mode.entries.firstOrNull {
        it.name.equals(this.name, ignoreCase = true)
    } ?: Mode.UNKNOWN
}
