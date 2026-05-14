package net.testiprod.entur.config

import net.testiprod.entur.logging.HttpLogLevel

open class EnturBaseConfig {
    lateinit var enturClientName: String
    var logLevel: HttpLogLevel = HttpLogLevel.ALL
}
