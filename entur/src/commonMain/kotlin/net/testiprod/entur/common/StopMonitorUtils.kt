package net.testiprod.entur.common

import net.testiprod.entur.common.exceptions.EnturParseException
import net.testiprod.entur.common.models.EstimatedCall
import kotlin.time.Instant

object StopMonitorUtils {
    /**
     * Parses dates in "2026-01-12T20:10:29+01:00" format.
     */
    fun parseDate(string: String): Instant {
        try {
            return Instant.parse(string)
        } catch (e: Exception) {
            throw EnturParseException("Failed to parse date string=$string", e)
        }
    }

    fun String?.toColorInt(): Int? = try {
        val hex = this!!
        hex.toInt(radix = 16) or 0xFF000000.toInt()
    } catch (e: Exception) {
        null
    }

    fun EstimatedCall.isDelayed(delayHysteresisMs: Long = 30_000L): Boolean = isDelayed(
        aimedDepartureTime,
        expectedDepartureTime,
        delayHysteresisMs,
    )

    fun isDelayed(
        aimedDepartureTime: Instant,
        expectedDepartureTime: Instant,
        delayHysteresisMs: Long = 30_000L,
    ): Boolean =
        expectedDepartureTime.toEpochMilliseconds() > (aimedDepartureTime.toEpochMilliseconds() + delayHysteresisMs)

    private const val WHITE = 0XFFFFFFFF.toInt()
    private const val BLACK = 0XFF000000.toInt()
}
