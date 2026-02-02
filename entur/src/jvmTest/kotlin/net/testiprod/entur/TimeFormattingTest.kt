package net.testiprod.entur

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import net.testiprod.entur.utils.calculateRealTime
import net.testiprod.entur.utils.toDateString
import net.testiprod.entur.utils.toHourMinuteString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class TimeFormattingTest {

    @Test
    fun `toHourMinuteString formats single digit hour and minute with padding`() {
        val localDateTime = LocalDateTime(2024, 1, 15, 5, 3, 0)
        val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
        assertEquals("05:03", instant.toHourMinuteString())
    }

    @Test
    fun `toHourMinuteString formats double digit hour and minute`() {
        val localDateTime = LocalDateTime(2024, 1, 15, 14, 45, 0)
        val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
        assertEquals("14:45", instant.toHourMinuteString())
    }

    @Test
    fun `toDateString formats date correctly`() {
        val localDateTime = LocalDateTime(2024, 1, 5, 10, 0, 0)
        val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
        assertEquals("2024.01.05", instant.toDateString())
    }

    @Test
    fun `toDateString formats leap year date correctly`() {
        val localDateTime = LocalDateTime(2024, 2, 29, 23, 59, 59)
        val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
        assertEquals("2024.02.29", instant.toDateString())
    }

    @Test
    fun `calculateRealTime returns minutes and seconds when more than a minute away`() {
        val now = Instant.parse("2024-01-15T10:00:00Z")
        val future = Instant.parse("2024-01-15T10:02:30Z")
        assertEquals("2m 30s", future.calculateRealTime(now))
    }

    @Test
    fun `calculateRealTime returns only seconds when less than a minute away`() {
        val now = Instant.parse("2024-01-15T10:00:00Z")
        val future = Instant.parse("2024-01-15T10:00:45Z")
        assertEquals("45s", future.calculateRealTime(now))
    }

    @Test
    fun `calculateRealTime returns Nå when time is now`() {
        val now = Instant.parse("2024-01-15T10:00:00Z")
        assertEquals("Nå", now.calculateRealTime(now))
    }

    @Test
    fun `calculateRealTime returns Nå when time is in the past`() {
        val now = Instant.parse("2024-01-15T10:00:00Z")
        val past = Instant.parse("2024-01-15T09:59:00Z")
        assertEquals("Nå", past.calculateRealTime(now))
    }

    @Test
    fun `calculateRealTime pads seconds with zero`() {
        val now = Instant.parse("2024-01-15T10:00:00Z")
        val future = Instant.parse("2024-01-15T10:01:05Z")
        assertEquals("1m 05s", future.calculateRealTime(now))
    }
}
