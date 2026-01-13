package net.testiprod.entur.vehicle.data

import net.testiprod.entur.apollographql.vehiclepositions.VehiclesQuery
import net.testiprod.entur.common.StopMonitorUtils
import net.testiprod.entur.common.exceptions.StopMonitorParseException
import net.testiprod.entur.vehicle.data.SubscriptionMarshaller.toDomain
import net.testiprod.entur.vehicle.domain.Location
import net.testiprod.entur.vehicle.domain.Vehicle

internal object QueryMarshaller {

    fun VehiclesQuery.Vehicle.toDomain(): Vehicle {
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

    fun VehiclesQuery.Location.toDomain(): Location = Location(latitude, longitude)
}
