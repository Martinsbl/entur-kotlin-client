package net.testiprod.entur.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Format an Instant to HH:mm format.
 */
fun Instant.toHourMinuteString(): String {
    val localDateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val hour = localDateTime.hour.toString().padStart(2, '0')
    val minute = localDateTime.minute.toString().padStart(2, '0')
    return "$hour:$minute"
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
