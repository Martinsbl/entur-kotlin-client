package net.testiprod.entur.common

import net.testiprod.entur.common.exceptions.EnturParseException
import net.testiprod.entur.common.models.EstimatedCall
import java.time.Instant
import java.util.Date

object StopMonitorUtils {
    fun parseDate(string: String): Date {
        try {
            val instant = Instant.parse(string)
            return Date.from(instant)
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
        aimedDepartureTime: Date,
        expectedDepartureTime: Date,
        delayHysteresisMs: Long = 30_000L,
    ): Boolean = expectedDepartureTime.time > (aimedDepartureTime.time + delayHysteresisMs)

    private const val WHITE = 0XFFFFFFFF.toInt()
    private const val BLACK = 0XFF000000.toInt()
}
