package net.testiprod.entur.logging

import org.slf4j.LoggerFactory

actual fun defaultPlatformSink(): LogSink = LogSink { level, tag, message, throwable ->
    val logger = LoggerFactory.getLogger(tag)
    when (level) {
        LogLevel.TRACE -> logger.trace(message, throwable)
        LogLevel.DEBUG -> logger.debug(message, throwable)
        LogLevel.INFO -> logger.info(message, throwable)
        LogLevel.WARN -> logger.warn(message, throwable)
        LogLevel.ERROR -> logger.error(message, throwable)
    }
}
