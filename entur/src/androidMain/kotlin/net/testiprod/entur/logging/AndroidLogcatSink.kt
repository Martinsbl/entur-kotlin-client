package net.testiprod.entur.logging

import android.util.Log

actual fun defaultPlatformSink(): LogSink = LogSink { level, tag, message, throwable ->
    val safeTag = if (tag.length > 23) tag.take(23) else tag
    when (level) {
        LogLevel.TRACE -> Log.v(safeTag, message, throwable)
        LogLevel.DEBUG -> Log.d(safeTag, message, throwable)
        LogLevel.INFO -> Log.i(safeTag, message, throwable)
        LogLevel.WARN -> Log.w(safeTag, message, throwable)
        LogLevel.ERROR -> Log.e(safeTag, message, throwable)
    }
}
