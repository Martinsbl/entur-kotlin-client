package net.testiprod.entur.journeyplanner.stopplace.api

import net.testiprod.entur.common.JOURNEY_PLANNER_BASE_URL
import net.testiprod.entur.config.EnturBaseConfig

class StopPlaceApiConfig : EnturBaseConfig() {
    var baseUrl: String = JOURNEY_PLANNER_BASE_URL
}
