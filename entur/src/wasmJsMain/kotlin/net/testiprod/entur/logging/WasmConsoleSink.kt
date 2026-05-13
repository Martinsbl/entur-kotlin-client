package net.testiprod.entur.logging

private external interface JsConsole {
    fun log(message: String)
    fun info(message: String)
    fun warn(message: String)
    fun error(message: String)
}

private external val console: JsConsole

actual fun defaultPlatformSink(): LogSink = LogSink { level, tag, message, throwable ->
    val formatted = "[$level] $tag: $message" +
        (if (throwable != null) "\n${throwable.stackTraceToString()}" else "")

    when (level) {
        LogLevel.TRACE, LogLevel.DEBUG -> console.log(formatted)
        LogLevel.INFO -> console.info(formatted)
        LogLevel.WARN -> console.warn(formatted)
        LogLevel.ERROR -> console.error(formatted)
    }
}
