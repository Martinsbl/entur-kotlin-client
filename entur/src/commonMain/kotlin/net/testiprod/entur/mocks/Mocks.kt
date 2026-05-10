package net.testiprod.entur.mocks

import net.testiprod.entur.common.models.DestinationDisplay
import net.testiprod.entur.common.models.DirectionType
import net.testiprod.entur.common.models.Line
import net.testiprod.entur.common.models.Presentation
import net.testiprod.entur.common.models.ServiceJourney
import net.testiprod.entur.common.models.Situation
import net.testiprod.entur.common.models.TransportMode
import net.testiprod.entur.journeyplanner.trip.models.Authority
import net.testiprod.entur.journeyplanner.trip.models.Leg
import net.testiprod.entur.journeyplanner.trip.models.Mode
import net.testiprod.entur.journeyplanner.trip.models.Operator
import net.testiprod.entur.journeyplanner.trip.models.Place
import net.testiprod.entur.journeyplanner.trip.models.Quay
import net.testiprod.entur.journeyplanner.trip.models.TransportSubMode
import net.testiprod.entur.journeyplanner.trip.models.Trip
import net.testiprod.entur.journeyplanner.trip.models.TripEstimatedCall
import net.testiprod.entur.journeyplanner.trip.models.TripPattern
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

// Quays
val mockQuayOsloS5 = Quay(
    name = "Oslo S",
    description = null,
    latitude = 59.910818,
    longitude = 10.755399,
    publicCode = "5",
)

val mockQuayOsloS3 = Quay(
    name = "Oslo S",
    description = null,
    latitude = 59.910956,
    longitude = 10.755531,
    publicCode = "3",
)

val mockQuayBrakeroyaStasjon2 = Quay(
    name = "Brakerøya stasjon",
    description = null,
    latitude = 59.743823,
    longitude = 10.233122,
    publicCode = "2",
)

val mockQuayBrakeroyaStasjon1 = Quay(
    name = "Brakerøya stasjon",
    description = null,
    latitude = 59.743856,
    longitude = 10.233041,
    publicCode = "1",
)

val mockQuayBrakeroyaStasjonBus = Quay(
    name = "Brakerøya stasjon",
    description = "",
    latitude = 59.743727,
    longitude = 10.231879,
    publicCode = null,
)

val mockQuayDrammenSykehusBrakeroya = Quay(
    name = "Drammen sykehus Brakerøya",
    description = null,
    latitude = 59.743042,
    longitude = 10.233885,
    publicCode = null,
)

val mockQuayFarmen = Quay(
    name = "Farmen",
    description = null,
    latitude = 59.75495,
    longitude = 10.13399,
    publicCode = null,
)

val mockQuayOsloBussterminal = Quay(
    name = "Oslo bussterminal",
    description = null,
    latitude = 59.911761,
    longitude = 10.758241,
    publicCode = "",
)

val mockQuayOsloBussterminalA11 = Quay(
    name = "Oslo bussterminal",
    description = "se oppdatert info i terminalbygg",
    latitude = 59.911681,
    longitude = 10.759307,
    publicCode = "A11",
)

val mockQuayDrammenBangelokka = Quay(
    name = "Drammen Bangeløkka",
    description = null,
    latitude = 59.73091,
    longitude = 10.216454,
    publicCode = null,
)

val mockQuayMadsWielsPlass = Quay(
    name = "Mads Wiels plass",
    description = null,
    latitude = 59.73335,
    longitude = 10.217712,
    publicCode = null,
)

val mockQuayValbrottveien = Quay(
    name = "Valbrottveien",
    description = null,
    latitude = 59.75666,
    longitude = 10.13682,
    publicCode = null,
)

val mockQuayStromsoTorgTamburgata = Quay(
    name = "Strømsø torg",
    description = "i Tamburgata",
    latitude = 59.739187,
    longitude = 10.201964,
    publicCode = null,
)

val mockQuayStromsoTorgH = Quay(
    name = "Strømsø torg",
    description = null,
    latitude = 59.739909,
    longitude = 10.202112,
    publicCode = "H",
)

val mockQuayLysakerStasjon2 = Quay(
    name = "Lysaker stasjon",
    description = null,
    latitude = 59.913508,
    longitude = 10.637254,
    publicCode = "2",
)

val mockQuayLysakerStasjonD = Quay(
    name = "Lysaker stasjon",
    description = null,
    latitude = 59.913048,
    longitude = 10.637649,
    publicCode = "D",
)

// Places
val mockPlaceOsloS5 = Place(
    name = "Oslo S",
    latitude = 59.910818,
    longitude = 10.755399,
    quay = mockQuayOsloS5,
)

val mockPlaceOsloS3 = Place(
    name = "Oslo S",
    latitude = 59.910956,
    longitude = 10.755531,
    quay = mockQuayOsloS3,
)

val mockPlaceHome = Place(
    name = "Home",
    latitude = 59.7569,
    longitude = 10.1357,
    quay = null,
)

val mockPlaceBrakeroyaStasjon2 = Place(
    name = "Brakerøya stasjon",
    latitude = 59.743823,
    longitude = 10.233122,
    quay = mockQuayBrakeroyaStasjon2,
)

val mockPlaceBrakeroyaStasjon1 = Place(
    name = "Brakerøya stasjon",
    latitude = 59.743856,
    longitude = 10.233041,
    quay = mockQuayBrakeroyaStasjon1,
)

val mockPlaceBrakeroyaStasjonBus = Place(
    name = "Brakerøya stasjon",
    latitude = 59.743727,
    longitude = 10.231879,
    quay = mockQuayBrakeroyaStasjonBus,
)

val mockPlaceDrammenSykehusBrakeroya = Place(
    name = "Drammen sykehus Brakerøya",
    latitude = 59.743042,
    longitude = 10.233885,
    quay = mockQuayDrammenSykehusBrakeroya,
)

val mockPlaceFarmen = Place(
    name = "Farmen",
    latitude = 59.75495,
    longitude = 10.13399,
    quay = mockQuayFarmen,
)

val mockPlaceOsloBussterminal = Place(
    name = "Oslo bussterminal",
    latitude = 59.911761,
    longitude = 10.758241,
    quay = mockQuayOsloBussterminal,
)

val mockPlaceOsloBussterminalA11 = Place(
    name = "Oslo bussterminal",
    latitude = 59.911681,
    longitude = 10.759307,
    quay = mockQuayOsloBussterminalA11,
)

val mockPlaceDrammenBangelokka = Place(
    name = "Drammen Bangeløkka",
    latitude = 59.73091,
    longitude = 10.216454,
    quay = mockQuayDrammenBangelokka,
)

val mockPlaceMadsWielsPlass = Place(
    name = "Mads Wiels plass",
    latitude = 59.73335,
    longitude = 10.217712,
    quay = mockQuayMadsWielsPlass,
)

val mockPlaceValbrottveien = Place(
    name = "Valbrottveien",
    latitude = 59.75666,
    longitude = 10.13682,
    quay = mockQuayValbrottveien,
)

val mockPlaceStromsoTorgTamburgata = Place(
    name = "Strømsø torg",
    latitude = 59.739187,
    longitude = 10.201964,
    quay = mockQuayStromsoTorgTamburgata,
)

val mockPlaceStromsoTorgH = Place(
    name = "Strømsø torg",
    latitude = 59.739909,
    longitude = 10.202112,
    quay = mockQuayStromsoTorgH,
)

val mockPlaceLysakerStasjon2 = Place(
    name = "Lysaker stasjon",
    latitude = 59.913508,
    longitude = 10.637254,
    quay = mockQuayLysakerStasjon2,
)

val mockPlaceLysakerStasjonD = Place(
    name = "Lysaker stasjon",
    latitude = 59.913048,
    longitude = 10.637649,
    quay = mockQuayLysakerStasjonD,
)

// Authorities
val mockAuthorityVy = Authority(name = "Vy")
val mockAuthorityBrakar = Authority(name = "Brakar")
val mockAuthorityFlixBus = Authority(name = "FlixBus")
val mockAuthorityNorwayBussekspress = Authority(name = "NOR-WAY Bussekspress")
val mockAuthorityVyBuss = Authority(name = "Vy Buss")

// Operators
val mockOperatorVY = Operator(name = "VY")
val mockOperatorVyBussAS = Operator(name = "Vy Buss AS")
val mockOperatorFlixBus = Operator(name = "FlixBus")
val mockOperatorHaukeliekspressen = Operator(name = "Haukeliekspressen")
val mockOperatorVyBuss = Operator(name = "Vy Buss")

// Situations
val mockLine13Situation = Situation(
    id = "ADFAH98YAGA87G8AGSDFG6F767S6FGDFGHDHJD65N6D5GH6D5",
    summary = "Planned engineering works on R13 line",
    description = "Due to planned engineering works, R13 services between Drammen and Dal will be replaced by buses on 25th January 2026.",
    advice = "Please allow extra travel time.",
    reportType = Situation.ReportType.INCIDENT,
    startTime = Instant.parse("2026-01-25T00:00:00+01:00"),
    endTime = Instant.parse("2026-01-25T23:59:59+01:00"),
)
val mockFewerCarriagesSituation = Situation(
    id = "ASDFASDFAFASDFGHSDIOFGHSDF8GHSUIGH8GHSIG",
    summary = "Fewer carriages",
    description = "There will be fewer carriages on R13 services between Drammen and Dal",
    advice = "Use alternative routes if possible.",
    reportType = Situation.ReportType.GENERAL,
    startTime = null,
    endTime = null,
)

// Lines
val mockLineR13 = Line(
    id = "VYG:Line:R13",
    name = "Drammen-Oslo S-Dal",
    publicCode = "R13",
    situations = emptyList(),
    transportMode = TransportMode.RAIL,
    transportSubMode = null,
    presentation = Presentation(
        textColor = 0xFFFFFFFF.toInt(),
        backgroundColor = 0xFFDF2027.toInt(),
    ),
)

val mockLineBus1 = Line(
    id = "BRA:Line:4_6001",
    name = "Drammen sykehus Brakerøya - Sørensvingen / Mjøndalen",
    publicCode = "1",
    situations = emptyList(),
    transportMode = TransportMode.BUS,
    transportSubMode = TransportSubMode.LOCAL_BUS,
    presentation = null,
)

val mockLineRE11 = Line(
    id = "VYG:Line:RE11",
    name = "Eidsvoll-Oslo S-Skien",
    publicCode = "RE11",
    situations = listOf(mockFewerCarriagesSituation),
    transportMode = TransportMode.RAIL,
    transportSubMode = null,
    presentation = Presentation(
        textColor = 0xFFFFFFFF.toInt(),
        backgroundColor = 0xFF44C8F5.toInt(),
    ),
)

val mockLineBus4 = Line(
    id = "BRA:Line:4_6004",
    name = "Kniveåsen - Bera",
    publicCode = "4",
    situations = emptyList(),
    transportMode = TransportMode.BUS,
    transportSubMode = TransportSubMode.LOCAL_BUS,
    presentation = null,
)

val mockLineFlixBusFX651 = Line(
    id = "FLI:Line:d1fc7afc75068aa6",
    name = "Oslo - Kristiansand",
    publicCode = "FX651",
    situations = emptyList(),
    transportMode = TransportMode.COACH,
    transportSubMode = TransportSubMode.NATIONAL_COACH,
    presentation = Presentation(
        textColor = 0xFFFFFFFF.toInt(),
        backgroundColor = 0xFF73D700.toInt(),
    ),
)

val mockLineNW180 = Line(
    id = "NWY:Line:ac668a26-0855-5f63-b8a4-5d542de6f7f7",
    name = "Haukeliekspressen",
    publicCode = "NW180",
    situations = emptyList(),
    transportMode = TransportMode.COACH,
    transportSubMode = TransportSubMode.NATIONAL_COACH,
    presentation = Presentation(
        textColor = 0xFFFFFFFF.toInt(),
        backgroundColor = 0xFF2A347A.toInt(),
    ),
)

val mockLineR14 = Line(
    id = "VYG:Line:R14",
    name = "Asker-Oslo S-Kongsvinger",
    publicCode = "R14",
    situations = listOf(mockFewerCarriagesSituation),
    transportMode = TransportMode.RAIL,
    transportSubMode = null,
    presentation = Presentation(
        textColor = 0xFFFFFFFF.toInt(),
        backgroundColor = 0xFFDF2027.toInt(),
    ),
)

val mockLineVY1 = Line(
    id = "VYX:Line:20",
    name = "VY1 Notodden-Oslo",
    publicCode = "VY1",
    situations = emptyList(),
    transportMode = TransportMode.COACH,
    transportSubMode = TransportSubMode.NATIONAL_COACH,
    presentation = Presentation(
        textColor = 0xFFFFFFFF.toInt(),
        backgroundColor = 0xFF00685E.toInt(),
    ),
)

// Service Journeys
val mockServiceJourneyR13 = ServiceJourney(
    id = "VYG:ServiceJourney:1652_443326-R",
    line = mockLineR13,
    directionType = DirectionType.UNKNOWN,
    situations = listOf(
        mockLine13Situation,
        mockFewerCarriagesSituation,
    ),
)

val mockServiceJourneyBus1 = ServiceJourney(
    id = "BRA:ServiceJourney:4_6001",
    line = mockLineBus1,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

val mockServiceJourneyRE11 = ServiceJourney(
    id = "VYG:ServiceJourney:RE11",
    line = mockLineRE11,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

val mockServiceJourneyBus4 = ServiceJourney(
    id = "BRA:ServiceJourney:4_6004",
    line = mockLineBus4,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

val mockServiceJourneyFlixBusFX651 = ServiceJourney(
    id = "FLI:ServiceJourney:d1fc7afc75068aa6",
    line = mockLineFlixBusFX651,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

val mockServiceJourneyNW180 = ServiceJourney(
    id = "NWY:ServiceJourney:ac668a26-0855-5f63-b8a4-5d542de6f7f7",
    line = mockLineNW180,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

val mockServiceJourneyR14 = ServiceJourney(
    id = "VYG:ServiceJourney:R14",
    line = mockLineR14,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

val mockServiceJourneyVY1 = ServiceJourney(
    id = "VYX:ServiceJourney:20",
    line = mockLineVY1,
    directionType = DirectionType.UNKNOWN,
    situations = emptyList(),
)

// Trip Pattern 1 - Legs
val mockLegPattern1Leg1 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T10:51:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:19:00+01:00"),
    authority = mockAuthorityVy,
    distance = 38525.75,
    duration = 1855.seconds,
    expectedEndTime = Instant.parse("2026-01-25T10:51:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:20:05+01:00"),
    fromPlace = mockPlaceOsloS5,
    mode = Mode.RAIL,
    operator = mockOperatorVY,
    realTime = true,
    ride = true,
    serviceJourney = mockServiceJourneyR13,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Dal")),
    toPlace = mockPlaceBrakeroyaStasjon2,
    transportSubMode = TransportSubMode.RAIL_REPLACEMENT_BUS,
)

val mockLegPattern1Leg2 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T10:53:24+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:51:00+01:00"),
    authority = null,
    distance = 154.51,
    duration = 144.seconds,
    expectedEndTime = Instant.parse("2026-01-25T10:53:24+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:51:00+01:00"),
    fromPlace = mockPlaceBrakeroyaStasjon2,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceDrammenSykehusBrakeroya,
    transportSubMode = null,
)

val mockLegPattern1Leg3 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:20:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:06:00+01:00"),
    authority = mockAuthorityBrakar,
    distance = 6192.34,
    duration = 840.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:20:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:06:00+01:00"),
    fromPlace = mockPlaceDrammenSykehusBrakeroya,
    mode = Mode.BUS,
    operator = mockOperatorVyBussAS,
    realTime = true,
    ride = true,
    serviceJourney = mockServiceJourneyBus1,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Drammen sykehus Brakerøya")),
    toPlace = mockPlaceFarmen,
    transportSubMode = TransportSubMode.LOCAL_BUS,
)

val mockLegPattern1Leg4 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:24:23+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:20:00+01:00"),
    authority = null,
    distance = 333.87,
    duration = 263.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:24:23+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:20:00+01:00"),
    fromPlace = mockPlaceFarmen,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceHome,
    transportSubMode = null,
)

val mockTripPattern1 = TripPattern(
    aimedEndTime = Instant.parse("2026-01-25T11:24:23+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:19:00+01:00"),
    duration = 3858.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:24:23+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:20:05+01:00"),
    streetDistance = 488.38,
    waitingTime = 756.seconds,
    walkTime = 407.seconds,
    legs = listOf(
        mockLegPattern1Leg1,
        mockLegPattern1Leg2,
        mockLegPattern1Leg3,
        mockLegPattern1Leg4,
    ),
)

// Trip Pattern 2 - Legs
val mockLegPattern2Leg1 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:09:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:39:00+01:00"),
    authority = mockAuthorityVy,
    distance = 38516.07,
    duration = 1800.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:09:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:39:00+01:00"),
    fromPlace = mockPlaceOsloS5,
    mode = Mode.RAIL,
    operator = mockOperatorVY,
    realTime = true,
    ride = true,
    serviceJourney = mockServiceJourneyRE11,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Eidsvoll")),
    toPlace = mockPlaceBrakeroyaStasjon1,
    transportSubMode = TransportSubMode.LONG_DISTANCE,
)

val mockLegPattern2Leg2 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:10:34+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:09:00+01:00"),
    authority = null,
    distance = 107.52,
    duration = 94.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:10:34+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:09:00+01:00"),
    fromPlace = mockPlaceBrakeroyaStasjon1,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceBrakeroyaStasjonBus,
    transportSubMode = null,
)

val mockLegPattern2Leg3 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:22:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:15:00+01:00"),
    authority = mockAuthorityVy,
    distance = 1750.55,
    duration = 420.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:22:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:15:00+01:00"),
    fromPlace = mockPlaceBrakeroyaStasjonBus,
    mode = Mode.BUS,
    operator = mockOperatorVY,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyR13,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Dal")),
    toPlace = mockPlaceStromsoTorgTamburgata,
    transportSubMode = TransportSubMode.RAIL_REPLACEMENT_BUS,
)

val mockLegPattern2Leg4 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:23:04+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:22:00+01:00"),
    authority = null,
    distance = 81.41,
    duration = 64.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:23:04+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:22:00+01:00"),
    fromPlace = mockPlaceStromsoTorgTamburgata,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceStromsoTorgH,
    transportSubMode = null,
)

val mockLegPattern2Leg5 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:35:00+01:00"),
    authority = mockAuthorityBrakar,
    distance = 4831.14,
    duration = 960.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:35:00+01:00"),
    fromPlace = mockPlaceStromsoTorgH,
    mode = Mode.BUS,
    operator = mockOperatorVyBussAS,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyBus4,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Kniveåsen")),
    toPlace = mockPlaceValbrottveien,
    transportSubMode = TransportSubMode.LOCAL_BUS,
)

val mockLegPattern2Leg6 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    authority = null,
    distance = 70.07,
    duration = 58.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    fromPlace = mockPlaceValbrottveien,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceHome,
    transportSubMode = null,
)

val mockTripPattern2 = TripPattern(
    aimedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:39:00+01:00"),
    duration = 4378.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:39:00+01:00"),
    streetDistance = 259.0,
    waitingTime = 982.seconds,
    walkTime = 216.seconds,
    legs = listOf(
        mockLegPattern2Leg1,
        mockLegPattern2Leg2,
        mockLegPattern2Leg3,
        mockLegPattern2Leg4,
        mockLegPattern2Leg5,
        mockLegPattern2Leg6,
    ),
)

// Trip Pattern 3 - Legs
val mockLegPattern3Leg1 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:24:03+01:00"),
    authority = null,
    distance = 442.4,
    duration = 357.seconds,
    expectedEndTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:24:03+01:00"),
    fromPlace = mockPlaceOsloS3,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceOsloBussterminal,
    transportSubMode = null,
)

val mockLegPattern3Leg2 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    authority = mockAuthorityFlixBus,
    distance = 37777.48,
    duration = 2280.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    fromPlace = mockPlaceOsloBussterminal,
    mode = Mode.COACH,
    operator = mockOperatorFlixBus,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyFlixBusFX651,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Oslo")),
    toPlace = mockPlaceDrammenBangelokka,
    transportSubMode = TransportSubMode.NATIONAL_COACH,
)

val mockLegPattern3Leg3 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:16:49+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    authority = null,
    distance = 616.73,
    duration = 529.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:16:49+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    fromPlace = mockPlaceDrammenBangelokka,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceMadsWielsPlass,
    transportSubMode = null,
)

val mockLegPattern3Leg4 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:31:00+01:00"),
    authority = mockAuthorityBrakar,
    distance = 6275.67,
    duration = 1200.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:31:00+01:00"),
    fromPlace = mockPlaceMadsWielsPlass,
    mode = Mode.BUS,
    operator = mockOperatorVyBussAS,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyBus4,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Kniveåsen")),
    toPlace = mockPlaceValbrottveien,
    transportSubMode = TransportSubMode.LOCAL_BUS,
)

val mockLegPattern3Leg5 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    authority = null,
    distance = 70.07,
    duration = 58.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    fromPlace = mockPlaceValbrottveien,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceHome,
    transportSubMode = null,
)

val mockTripPattern3 = TripPattern(
    aimedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:24:03+01:00"),
    duration = 5275.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:24:03+01:00"),
    streetDistance = 1129.2,
    waitingTime = 851.seconds,
    walkTime = 944.seconds,
    legs = listOf(
        mockLegPattern3Leg1,
        mockLegPattern3Leg2,
        mockLegPattern3Leg3,
        mockLegPattern3Leg4,
        mockLegPattern3Leg5,
    ),
)

// Trip Pattern 4 - Legs
val mockLegPattern4Leg1 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:23:06+01:00"),
    authority = null,
    distance = 512.25,
    duration = 414.seconds,
    expectedEndTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:23:06+01:00"),
    fromPlace = mockPlaceOsloS3,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceOsloBussterminalA11,
    transportSubMode = null,
)

val mockLegPattern4Leg2 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:15:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    authority = mockAuthorityNorwayBussekspress,
    distance = 45296.09,
    duration = 2700.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:15:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:30:00+01:00"),
    fromPlace = mockPlaceOsloBussterminalA11,
    mode = Mode.COACH,
    operator = mockOperatorHaukeliekspressen,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyNW180,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Haukeligrend")),
    toPlace = mockPlaceDrammenBangelokka,
    transportSubMode = TransportSubMode.NATIONAL_COACH,
)

val mockLegPattern4Leg3 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:23:49+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:15:00+01:00"),
    authority = null,
    distance = 616.73,
    duration = 529.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:23:49+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:15:00+01:00"),
    fromPlace = mockPlaceDrammenBangelokka,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceMadsWielsPlass,
    transportSubMode = null,
)

val mockLegPattern4Leg4 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:31:00+01:00"),
    authority = mockAuthorityBrakar,
    distance = 6275.67,
    duration = 1200.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:31:00+01:00"),
    fromPlace = mockPlaceMadsWielsPlass,
    mode = Mode.BUS,
    operator = mockOperatorVyBussAS,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyBus4,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Kniveåsen")),
    toPlace = mockPlaceValbrottveien,
    transportSubMode = TransportSubMode.LOCAL_BUS,
)

val mockLegPattern4Leg5 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    authority = null,
    distance = 70.07,
    duration = 58.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:51:00+01:00"),
    fromPlace = mockPlaceValbrottveien,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceHome,
    transportSubMode = null,
)

val mockTripPattern4 = TripPattern(
    aimedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:23:06+01:00"),
    duration = 5332.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:51:58+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:23:06+01:00"),
    streetDistance = 1199.05,
    waitingTime = 431.seconds,
    walkTime = 1001.seconds,
    legs = listOf(
        mockLegPattern4Leg1,
        mockLegPattern4Leg2,
        mockLegPattern4Leg3,
        mockLegPattern4Leg4,
        mockLegPattern4Leg5,
    ),
)

// Trip Pattern 5 - Legs
val mockLegPattern5Leg1 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:59:00+01:00"),
    authority = mockAuthorityVy,
    distance = 7203.95,
    duration = 540.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:59:00+01:00"),
    fromPlace = mockPlaceOsloS5,
    mode = Mode.RAIL,
    operator = mockOperatorVY,
    realTime = true,
    ride = true,
    serviceJourney = mockServiceJourneyR14,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Kongsvinger stasjon")),
    toPlace = mockPlaceLysakerStasjon2,
    transportSubMode = TransportSubMode.LOCAL,
)

val mockLegPattern5Leg2 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:09:35+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    authority = null,
    distance = 104.87,
    duration = 95.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:09:35+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:08:00+01:00"),
    fromPlace = mockPlaceLysakerStasjon2,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceLysakerStasjonD,
    transportSubMode = null,
)

val mockLegPattern5Leg3 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:50:00+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:12:00+01:00"),
    authority = mockAuthorityVyBuss,
    distance = 39876.6,
    duration = 2280.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:50:00+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:12:00+01:00"),
    fromPlace = mockPlaceLysakerStasjonD,
    mode = Mode.COACH,
    operator = mockOperatorVyBuss,
    realTime = false,
    ride = true,
    serviceJourney = mockServiceJourneyVY1,
    toEstimatedCall = TripEstimatedCall(DestinationDisplay(frontText = "Oslo")),
    toPlace = mockPlaceFarmen,
    transportSubMode = TransportSubMode.NATIONAL_COACH,
)

val mockLegPattern5Leg4 = Leg(
    aimedEndTime = Instant.parse("2026-01-25T11:54:23+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T11:50:00+01:00"),
    authority = null,
    distance = 333.87,
    duration = 263.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:54:23+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T11:50:00+01:00"),
    fromPlace = mockPlaceFarmen,
    mode = Mode.FOOT,
    operator = null,
    realTime = false,
    ride = false,
    serviceJourney = null,
    toEstimatedCall = null,
    toPlace = mockPlaceHome,
    transportSubMode = null,
)

val mockTripPattern5 = TripPattern(
    aimedEndTime = Instant.parse("2026-01-25T11:54:23+01:00"),
    aimedStartTime = Instant.parse("2026-01-25T10:59:00+01:00"),
    duration = 3323.seconds,
    expectedEndTime = Instant.parse("2026-01-25T11:54:23+01:00"),
    expectedStartTime = Instant.parse("2026-01-25T10:59:00+01:00"),
    streetDistance = 438.74,
    waitingTime = 145.seconds,
    walkTime = 358.seconds,
    legs = listOf(
        mockLegPattern5Leg1,
        mockLegPattern5Leg2,
        mockLegPattern5Leg3,
        mockLegPattern5Leg4,
    ),
)

// Main Trip
val mockTripOsloToHome = Trip(
    from = mockPlaceOsloS5,
    to = mockPlaceHome,
    tripPatterns = listOf(
        mockTripPattern1,
        mockTripPattern2,
        mockTripPattern3,
        mockTripPattern4,
        mockTripPattern5,
    ),
)
