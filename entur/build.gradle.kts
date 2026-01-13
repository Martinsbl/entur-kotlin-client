plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.apollographql.apollo")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    `maven-publish`
}

group = "net.testiprod.entur"
version = "0.0.1-SNAPSHOT"

android {
    namespace = "net.testiprod.entur"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    jvmToolchain(17)

    jvm()

    androidTarget {
        publishLibraryVariants("release")
    }

    wasmJs {
        browser()
    }

    sourceSets {
        val ktorVersion = "3.3.3"
        commonMain.dependencies {
            implementation("com.apollographql.ktor:apollo-engine-ktor:0.1.1")
            implementation("com.apollographql.apollo:apollo-runtime:4.3.3")

            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

            implementation("ch.qos.logback:logback-classic:1.5.24")
        }

        jvmMain.dependencies {
            implementation("io.ktor:ktor-client-cio:$ktorVersion")
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
        }

        wasmJsMain.dependencies {
            implementation("io.ktor:ktor-client-js:$ktorVersion")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

apollo {
    service("journeyplanner") {
        packageName.set("net.testiprod.entur.apollographql.journeyplanner")
        srcDir("src/commonMain/graphql/journeyplanner")
        generateAsInternal.set(true)
    }

    service("vehiclepositions") {
        packageName.set("net.testiprod.entur.apollographql.vehiclepositions")
        srcDir("src/commonMain/graphql/vehiclepositions")
        generateAsInternal.set(true)
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}
