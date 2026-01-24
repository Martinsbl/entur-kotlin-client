package net.testiprod.entur.utils

import net.testiprod.entur.common.models.Presentation

/**
 * https://brakar.no/ruter/
 */
fun getBrakarPresentations(publicCode: String?) = when (publicCode) {
    "1" -> Presentation(0xFFf5c445.toInt(), BLACK)
    "2", "14", "15" -> Presentation(0xFFda3832.toInt(), WHITE)
    "3" -> Presentation(0xFFe47f3a.toInt(), WHITE)
    "4", "5" -> Presentation(0xFF006fb6.toInt(), WHITE)
    "16", "22", "24" -> Presentation(0xFF48bff0.toInt(), BLACK)
    "52" -> Presentation(0xFF2ca958.toInt(), WHITE)
    "71" -> Presentation(0xFFadcc51.toInt(), BLACK)
    "100", "101", "73", "261", "251", "61", "63" -> Presentation(0xFF822c88.toInt(), WHITE)
    else -> Presentation(WHITE, BLACK)
}

private const val WHITE = 0XFFFFFFFF.toInt()
private const val BLACK = 0XFF000000.toInt()
