package net.testiprod.entur.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToLong
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.Instant
import kotlin.time.toDuration

/**
 * Format an Instant to HH:mm format.
 */
fun Instant.toHourMinuteString(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val hour = localDateTime.hour.toString().padStart(2, '0')
    val minute = localDateTime.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}

/**
 * Format an Instant to a String in the format "YYYY.MM.DD".
 */
fun Instant.toDateString(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val year = localDateTime.year
    val month = localDateTime.month.number.toString().padStart(2, '0')
    val day = localDateTime.day.toString().padStart(2, '0')
    return "$year.$month.$day"
}

fun Instant.calculateRealTime(now: Instant = Clock.System.now()): String {
    val diff = (this - now).inWholeMilliseconds
    val minutes = diff / 60_000
    val seconds = (diff % 60_000) / 1000

    return when {
        minutes > 0 -> "${minutes}m ${seconds.toString().padStart(2, '0')}s"
        seconds <= 0 -> "Nå"
        else -> "${seconds}s"
    }
}

fun Duration.roundToNearestUnit(unit: DurationUnit = DurationUnit.MINUTES): Duration {
    return toDouble(unit).roundToLong().toDuration(unit)
}

fun Duration.toMinutesSecondsString(): String {
    val rounded = roundToNearestUnit(DurationUnit.SECONDS)
    val minutes = rounded.inWholeMinutes
    val seconds = rounded.inWholeSeconds % 60

    return when {
        minutes > 0 -> "${minutes}m ${seconds}s"
        else -> "${seconds}s"
    }
}
