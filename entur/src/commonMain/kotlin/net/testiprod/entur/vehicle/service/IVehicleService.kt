package net.testiprod.entur.vehicle.service

import kotlinx.coroutines.flow.Flow
import net.testiprod.entur.vehicle.models.Vehicle

interface IVehicleService {

    // TODO Flow for subscription state?

    fun getVehicleFlow(serviceJourneyId: String): Flow<List<Vehicle>>
}
