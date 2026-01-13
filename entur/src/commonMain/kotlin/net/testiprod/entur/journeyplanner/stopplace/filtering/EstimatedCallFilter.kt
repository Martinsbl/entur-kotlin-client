package net.testiprod.entur.journeyplanner.stopplace.filtering

import net.testiprod.entur.common.models.EstimatedCall

fun interface EstimatedCallFilter {
    fun matches(estimatedCall: EstimatedCall): Boolean

    companion object {
        fun List<EstimatedCall>.applyFilters(filters: List<EstimatedCallFilter>?): List<EstimatedCall> {
            if (filters.isNullOrEmpty()) return this
            return filter { call -> filters.all { it.matches(call) } }
        }
    }
}
