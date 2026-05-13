package net.testiprod.entur.logging

import kotlin.concurrent.Volatile

object EnturLog {
    @Volatile
    private var sink: LogSink = NoOpSink

    @Volatile
    private var minLevel: LogLevel = LogLevel.INFO

    fun configure(block: Config.() -> Unit) {
        val config = Config()
        config.block()
        this.sink = config.sink
        this.minLevel = config.minLevel
    }

    fun logger(tag: String): Logger = Logger(tag)

    inline fun <reified T> logger(): Logger = logger(T::class.simpleName ?: "EnturLog")

    internal fun logInternal(
        level: LogLevel,
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        if (level.ordinal >= minLevel.ordinal) {
            sink.log(level, tag, message, throwable)
        }
    }

    class Logger internal constructor(private val tag: String) {
        fun t(
            message: String,
            throwable: Throwable? = null,
        ) = logInternal(LogLevel.TRACE, tag, message, throwable)

        fun d(
            message: String,
            throwable: Throwable? = null,
        ) = logInternal(LogLevel.DEBUG, tag, message, throwable)

        fun i(
            message: String,
            throwable: Throwable? = null,
        ) = logInternal(LogLevel.INFO, tag, message, throwable)

        fun w(
            message: String,
            throwable: Throwable? = null,
        ) = logInternal(LogLevel.WARN, tag, message, throwable)

        fun e(
            message: String,
            throwable: Throwable? = null,
        ) = logInternal(LogLevel.ERROR, tag, message, throwable)
    }

    class Config {
        var sink: LogSink = NoOpSink
        var minLevel: LogLevel = LogLevel.INFO
    }
}

expect fun defaultPlatformSink(): LogSink
