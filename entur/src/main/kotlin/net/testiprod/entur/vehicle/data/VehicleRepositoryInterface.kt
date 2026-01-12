package net.testiprod.entur.vehicle.data

import kotlinx.coroutines.flow.Flow
import net.testiprod.entur.vehicle.domain.Vehicle

interface VehicleRepositoryInterface {

    // TODO Flow for subscription state?

    fun getVehicleFlow(
        codeSpaceId: String?,
        lineRef: String?,
    ): Flow<List<Vehicle>>
}
