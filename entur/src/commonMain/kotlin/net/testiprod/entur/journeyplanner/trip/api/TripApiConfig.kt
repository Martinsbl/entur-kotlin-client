package net.testiprod.entur.journeyplanner.trip.api

import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.config.EnturBaseConfig

class TripApiConfig : EnturBaseConfig() {
    var baseUrl: String = JOURNEY_PLANNER_BASE_URL
}
