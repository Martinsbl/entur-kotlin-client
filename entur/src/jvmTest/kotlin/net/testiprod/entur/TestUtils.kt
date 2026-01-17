package net.testiprod.entur

fun getResourceFileAsText(fileName: String): String {
    val resource = object {}.javaClass.classLoader.getResourceAsStream(fileName)
    return resource?.bufferedReader()?.use { it.readText() }
        ?: throw IllegalArgumentException("Resource not found: $fileName")
}
