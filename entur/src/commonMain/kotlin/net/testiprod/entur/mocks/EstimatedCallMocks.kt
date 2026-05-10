package net.testiprod.entur.mocks

import net.testiprod.entur.common.models.DestinationDisplay
import net.testiprod.entur.common.models.EstimatedCall
import net.testiprod.entur.common.models.OccupancyStatus
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

val mockEstimatedCall = EstimatedCall(
    destinationDisplay = DestinationDisplay(
        frontText = "Oslo S",
    ),
    expectedDepartureTime = Clock.System.now().plus(321.seconds),
    aimedDepartureTime = Clock.System.now().plus(193.seconds),
    realTime = true,
    occupancyStatus = OccupancyStatus.FEW_SEATS_AVAILABLE,
    serviceJourney = mockServiceJourneyR13,
    situations = listOf(
        mockLine13Situation,
        mockFewerCarriagesSituation,
    ),
)
