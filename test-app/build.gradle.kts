plugins {
    kotlin("jvm")
}

group = "net.testiprod"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":entur"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
