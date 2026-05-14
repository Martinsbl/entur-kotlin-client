package net.testiprod.entur.vehicle.api

import net.testiprod.entur.common.VEHICLES_BASE_URL
import net.testiprod.entur.common.VEHICLE_SUBSCRIPTION_BASE_URL
import net.testiprod.entur.config.EnturBaseConfig

class VehicleApiConfig : EnturBaseConfig() {
    var baseUrl: String = VEHICLES_BASE_URL
    var baseUrlSubscription: String = VEHICLE_SUBSCRIPTION_BASE_URL
}
