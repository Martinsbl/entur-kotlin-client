package net.testiprod.entur.journeyplanner.stopplace.filtering

import net.testiprod.entur.common.models.EstimatedCall

class LineDirectionFilter(private val lines: List<LineFilter>) : EstimatedCallFilter {
    override fun matches(estimatedCall: EstimatedCall): Boolean {
        val serviceJourney = estimatedCall.serviceJourney ?: return false
        return lines.any { filter ->
            serviceJourney.line.publicCode == filter.publicCode &&
                (filter.direction == null || serviceJourney.directionType == filter.direction)
        }
    }
}
