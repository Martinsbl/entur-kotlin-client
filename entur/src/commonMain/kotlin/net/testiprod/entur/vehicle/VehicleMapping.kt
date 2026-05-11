package net.testiprod.entur.vehicle

import net.testiprod.entur.apollographql.vehiclepositions.VehiclesQuery
import net.testiprod.entur.apollographql.vehiclepositions.VehiclesSubscription
import net.testiprod.entur.apollographql.vehiclepositions.fragment.VehicleUpdateFragment
import net.testiprod.entur.common.exceptions.StopMonitorParseException
import net.testiprod.entur.common.models.OccupancyStatus
import net.testiprod.entur.vehicle.models.Location
import net.testiprod.entur.vehicle.models.Vehicle
import kotlin.time.Instant

internal fun VehiclesSubscription.Vehicle.toDomain(): Vehicle {
    return vehicleUpdateFragment.toDomain()
}

internal fun VehiclesQuery.Vehicle.toDomain(): Vehicle {
    return vehicleUpdateFragment.toDomain()
}

private fun VehicleUpdateFragment.toDomain(): Vehicle {
    try {
        return Vehicle(
            serviceJourney!!.id,
            line?.lineRef,
            line?.publicCode,
            line?.lineName,
            Instant.fromEpochSeconds(lastUpdatedEpochSecond!!.toLong()),
            delay?.toInt(),
            destinationName,
            bearing,
            inCongestion,
            occupancyStatus.toDomain(),
            speed,
            location!!.toDomain(),
        )
    } catch (e: Exception) {
        throw StopMonitorParseException(
            "Failed to parse $this.",
            e,
        )
    }
}

internal fun VehicleUpdateFragment.Location.toDomain(): Location {
    return Location(latitude, longitude)
}

internal fun net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus?.toDomain(): OccupancyStatus? {
    return when (this) {
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.crushedStandingRoomOnly -> OccupancyStatus.CRUSHED_STANDING_ROOM_ONLY
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.empty -> OccupancyStatus.EMPTY
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.fewSeatsAvailable -> OccupancyStatus.FEW_SEATS_AVAILABLE
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.full -> OccupancyStatus.FULL
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.manySeatsAvailable -> OccupancyStatus.MANY_SEATS_AVAILABLE
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.noData -> null
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.notAcceptingPassengers -> OccupancyStatus.NOT_ACCEPTING_PASSENGERS
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.standingRoomOnly -> OccupancyStatus.STANDING_ROOM_ONLY
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.seatsAvailable -> OccupancyStatus.SEATS_AVAILABLE
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.standingAvailable -> OccupancyStatus.STANDING_AVAILABLE
        net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.UNKNOWN__ -> OccupancyStatus.UNKNOWN
        null -> OccupancyStatus.UNKNOWN
    }
}
