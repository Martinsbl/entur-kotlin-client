package net.testiprod

import net.testiprod.net.testiprod.entur.TestClass

fun main() {
    val name = "Kotlin"
    val testClass = TestClass()
    println(testClass.print())

    for (i in 1..5) {
        // TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}
