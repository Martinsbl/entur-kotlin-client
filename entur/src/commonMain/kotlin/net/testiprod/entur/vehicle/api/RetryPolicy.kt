package net.testiprod.entur.vehicle.api

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class RetryPolicy(
    val maxAttempts: Long = 5,
    val shouldRetry: (Throwable) -> Boolean = { true },
    val delay: (attempt: Long) -> Duration = { attempt -> minOf((attempt + 1).seconds, 30.seconds) },
) {
    companion object {
        val Default = RetryPolicy()
        val NoRetry = RetryPolicy(maxAttempts = 0)
    }
}
