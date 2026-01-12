package net.testiprod.entur.vehicle.data

import net.testiprod.entur.apollographql.vehiclepositions.VehiclesSubscription
import net.testiprod.entur.common.StopMonitorUtils
import net.testiprod.entur.common.exceptions.StopMonitorParseException
import net.testiprod.entur.common.models.OccupancyStatus
import net.testiprod.entur.vehicle.domain.Location
import net.testiprod.entur.vehicle.domain.Vehicle

internal object SubscriptionMarshaller {

    fun VehiclesSubscription.Vehicle.toDomain(): Vehicle {
        try {
            return Vehicle(
                serviceJourney?.id as String,
                line?.lineRef,
                line?.publicCode,
                line?.lineName,
                StopMonitorUtils.parseDate(lastUpdated as String),
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
                "Failed to parse $this. serviceJourney=$serviceJourney",
                e,
            )
        }
    }

    fun VehiclesSubscription.Location.toDomain(): Location {
        return Location(latitude, longitude)
    }

    fun net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus?.toDomain(): OccupancyStatus? {
        return when (this) {
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.crushedStandingRoomOnly -> OccupancyStatus.CRUSHED_STANDING_ROOM_ONLY
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.empty -> OccupancyStatus.EMPTY
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.fewSeatsAvailable -> OccupancyStatus.FEW_SEATS_AVAILABLE
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.full -> OccupancyStatus.FULL
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.manySeatsAvailable -> OccupancyStatus.MANY_SEATS_AVAILABLE
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.noData -> null
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.notAcceptingPassengers -> OccupancyStatus.NOT_ACCEPTING_PASSENGERS
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.standingRoomOnly -> OccupancyStatus.STANDING_ROOM_ONLY
            net.testiprod.entur.apollographql.vehiclepositions.type.OccupancyStatus.UNKNOWN__ -> OccupancyStatus.UNKNOWN
            null -> OccupancyStatus.UNKNOWN
        }
    }
}
