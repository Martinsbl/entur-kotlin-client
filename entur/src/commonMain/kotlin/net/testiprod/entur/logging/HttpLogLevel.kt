package net.testiprod.entur.logging

import io.ktor.client.plugins.logging.LogLevel

enum class HttpLogLevel {
    ALL,
    HEADERS,
    BODY,
    INFO,
    NONE,
    ;

    companion object {
        fun HttpLogLevel.toKtorLoglevel(): LogLevel {
            return when (this) {
                ALL -> LogLevel.ALL
                HEADERS -> LogLevel.HEADERS
                BODY -> LogLevel.BODY
                INFO -> LogLevel.INFO
                NONE -> LogLevel.NONE
            }
        }
    }
}
