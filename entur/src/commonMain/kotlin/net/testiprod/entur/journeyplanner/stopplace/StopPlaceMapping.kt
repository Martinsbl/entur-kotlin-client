package net.testiprod.entur.journeyplanner.stopplace

import net.testiprod.entur.apollographql.journeyplanner.QuayQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceQuery
import net.testiprod.entur.common.toDomain
import net.testiprod.entur.journeyplanner.stopplace.models.Quay
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceDetails

internal fun StopPlaceDetailsQuery.StopPlace.toDomain(): StopPlaceDetails = StopPlaceDetails(
    id = id,
    name = name,
    quays = this.quays?.mapNotNull { it?.toDomain() },
    estimatedCalls = emptyList(),
)

internal fun StopPlaceQuery.StopPlace.toDomain(): StopPlaceDetails = StopPlaceDetails(
    name,
    id,
    emptyList(),
    estimatedCalls.map { it.estimatedCallFragment.toDomain() },
)

internal fun StopPlaceDetailsQuery.Quay.toDomain(): Quay = Quay(
    id = id,
    name = name,
    publicCode = publicCode,
    lines = lines.map { it.toDomain() },
    journeyPatterns = journeyPatterns.mapNotNull { it?.toDomain() },
    estimatedCalls = emptyList(),
)

internal fun QuayQuery.Quay.toDomain(): Quay = Quay(
    id = id,
    name = name,
    publicCode = null,
    lines = emptyList(),
    journeyPatterns = emptyList(),
    estimatedCalls = estimatedCalls.map { it.estimatedCallFragment.toDomain() },
)
