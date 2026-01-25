package net.testiprod.entur.common

import net.testiprod.entur.apollographql.journeyplanner.fragment.EstimatedCallFragment
import net.testiprod.entur.apollographql.journeyplanner.fragment.SituationsFragment
import net.testiprod.entur.common.StopMonitorUtils.toColorInt
import net.testiprod.entur.common.models.DestinationDisplay
import net.testiprod.entur.common.models.DirectionType
import net.testiprod.entur.common.models.DirectionType.ANTICLOCKWISE
import net.testiprod.entur.common.models.DirectionType.CLOCKWISE
import net.testiprod.entur.common.models.DirectionType.INBOUND
import net.testiprod.entur.common.models.DirectionType.OUTBOUND
import net.testiprod.entur.common.models.DirectionType.UNKNOWN
import net.testiprod.entur.common.models.EstimatedCall
import net.testiprod.entur.common.models.JourneyPattern
import net.testiprod.entur.common.models.Line
import net.testiprod.entur.common.models.OccupancyStatus
import net.testiprod.entur.common.models.OccupancyStatus.CRUSHED_STANDING_ROOM_ONLY
import net.testiprod.entur.common.models.OccupancyStatus.EMPTY
import net.testiprod.entur.common.models.OccupancyStatus.FEW_SEATS_AVAILABLE
import net.testiprod.entur.common.models.OccupancyStatus.FULL
import net.testiprod.entur.common.models.OccupancyStatus.MANY_SEATS_AVAILABLE
import net.testiprod.entur.common.models.OccupancyStatus.NOT_ACCEPTING_PASSENGERS
import net.testiprod.entur.common.models.OccupancyStatus.STANDING_ROOM_ONLY
import net.testiprod.entur.common.models.Presentation
import net.testiprod.entur.common.models.ServiceJourney
import net.testiprod.entur.common.models.Situation
import net.testiprod.entur.common.models.Situation.ReportType
import net.testiprod.entur.common.models.Situation.ReportType.GENERAL
import net.testiprod.entur.common.models.Situation.ReportType.INCIDENT
import net.testiprod.entur.common.models.TransportMode
import net.testiprod.entur.common.models.TransportMode.AIR
import net.testiprod.entur.common.models.TransportMode.BUS
import net.testiprod.entur.common.models.TransportMode.CABLEWAY
import net.testiprod.entur.common.models.TransportMode.COACH
import net.testiprod.entur.common.models.TransportMode.FUNICULAR
import net.testiprod.entur.common.models.TransportMode.LIFT
import net.testiprod.entur.common.models.TransportMode.METRO
import net.testiprod.entur.common.models.TransportMode.MONORAIL
import net.testiprod.entur.common.models.TransportMode.RAIL
import net.testiprod.entur.common.models.TransportMode.TAXI
import net.testiprod.entur.common.models.TransportMode.TRAM
import net.testiprod.entur.common.models.TransportMode.TROLLEYBUS
import net.testiprod.entur.common.models.TransportMode.WATER
import net.testiprod.entur.journeyplanner.trip.models.TransportSubMode
import net.testiprod.entur.apollographql.journeyplanner.StopPlaceDetailsQuery.JourneyPattern as EnturJourneyPattern
import net.testiprod.entur.apollographql.journeyplanner.fragment.EstimatedCallFragment.ServiceJourney as EnturServiceJourney
import net.testiprod.entur.apollographql.journeyplanner.fragment.LinesFragment as EnturLine
import net.testiprod.entur.apollographql.journeyplanner.fragment.LinesFragment.Presentation as EnturPresentation
import net.testiprod.entur.apollographql.journeyplanner.type.DirectionType as EnturDirectionType
import net.testiprod.entur.apollographql.journeyplanner.type.ReportType as EnturReportType
import net.testiprod.entur.apollographql.journeyplanner.type.TransportMode as EnturTransportMode

internal fun EnturDirectionType.toDomain(): DirectionType = when (this) {
    EnturDirectionType.unknown -> UNKNOWN
    EnturDirectionType.outbound -> OUTBOUND
    EnturDirectionType.inbound -> INBOUND
    EnturDirectionType.clockwise -> CLOCKWISE
    EnturDirectionType.anticlockwise -> ANTICLOCKWISE
    EnturDirectionType.UNKNOWN__ -> UNKNOWN
}

internal fun EnturJourneyPattern.toDomain(): JourneyPattern = JourneyPattern(
    directionType = directionType?.toDomain(),
)

internal fun EstimatedCallFragment.toDomain(): EstimatedCall = EstimatedCall(
    DestinationDisplay(destinationDisplay?.frontText),
    StopMonitorUtils.parseDate(expectedDepartureTime as String),
    StopMonitorUtils.parseDate(aimedDepartureTime as String),
    realtime,
    occupancyStatus.toDomain(),
    serviceJourney.toDomain(),
    getAllSituations(),
)

private fun EstimatedCallFragment.getAllSituations(): List<Situation> {
    val situations = situations.map { it.situationsFragment.toDomain() }.toMutableList()
    situations.addAll(serviceJourney.situations.map { it.situationsFragment.toDomain() })
    situations.addAll(serviceJourney.line.linesFragment.situations.map { it.situationsFragment.toDomain() })
    return situations.distinct()
}

internal fun EnturLine.toDomain(): Line = Line(
    id = id,
    name = name,
    publicCode = publicCode,
    presentation = presentation?.toDomain(),
    transportMode = transportMode.toDomain(),
    transportSubMode = transportSubmode?.let { TransportSubMode.fromValue(it.rawValue) },
)

internal fun net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus?.toDomain(): OccupancyStatus? =
    when (this) {
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.crushedStandingRoomOnly -> CRUSHED_STANDING_ROOM_ONLY
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.empty -> EMPTY
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.fewSeatsAvailable -> FEW_SEATS_AVAILABLE
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.full -> FULL
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.manySeatsAvailable -> MANY_SEATS_AVAILABLE
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.noData -> null
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.notAcceptingPassengers -> NOT_ACCEPTING_PASSENGERS
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.standingRoomOnly -> STANDING_ROOM_ONLY
        net.testiprod.entur.apollographql.journeyplanner.type.OccupancyStatus.UNKNOWN__ -> OccupancyStatus.UNKNOWN
        null -> OccupancyStatus.UNKNOWN
    }

internal fun EnturPresentation.toDomain(): Presentation = Presentation(
    backgroundColor = this.colour.toColorInt(),
    textColor = this.textColour.toColorInt(),
)

internal fun EnturServiceJourney.toDomain(): ServiceJourney = ServiceJourney(
    id,
    directionType?.toDomain(),
    line.linesFragment.toDomain(),
)

internal fun EnturReportType?.toDomain(): ReportType = when (this) {
    EnturReportType.general -> GENERAL
    EnturReportType.incident -> INCIDENT
    EnturReportType.UNKNOWN__ -> ReportType.UNKNOWN
    null -> ReportType.UNKNOWN
}

internal fun SituationsFragment.toDomain(): Situation = Situation(
    id,
    summary.firstOrNull()?.value,
    description.firstOrNull()?.value,
    advice.firstOrNull()?.value,
    reportType.toDomain(),
    validityPeriod?.startTime?.let { StopMonitorUtils.parseDate(it as String) },
    validityPeriod?.endTime?.let { StopMonitorUtils.parseDate(it as String) },
)

internal fun EnturTransportMode?.toDomain(): TransportMode {
    return when (this) {
        EnturTransportMode.air -> AIR
        EnturTransportMode.bus -> BUS
        EnturTransportMode.cableway -> CABLEWAY
        EnturTransportMode.water -> WATER
        EnturTransportMode.funicular -> FUNICULAR
        EnturTransportMode.lift -> LIFT
        EnturTransportMode.rail -> RAIL
        EnturTransportMode.metro -> METRO
        EnturTransportMode.taxi -> TAXI
        EnturTransportMode.tram -> TRAM
        EnturTransportMode.trolleybus -> TROLLEYBUS
        EnturTransportMode.monorail -> MONORAIL
        EnturTransportMode.coach -> COACH
        EnturTransportMode.unknown -> TransportMode.UNKNOWN
        EnturTransportMode.UNKNOWN__ -> TransportMode.UNKNOWN
        null -> TransportMode.UNKNOWN
    }
}
