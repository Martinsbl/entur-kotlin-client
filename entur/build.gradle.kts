plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.apollographql.apollo")
    id("com.android.library")
    id("org.jlleitschuh.gradle.ktlint")
    `maven-publish`
}

group = "net.testiprod.entur"
version = "0.0.2-SNAPSHOT"

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
        val coroutinesVersion = "1.10.2"
        commonMain.dependencies {
            implementation("com.apollographql.ktor:apollo-engine-ktor:0.1.1")
            implementation("com.apollographql.apollo:apollo-runtime:4.3.3")

            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
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
            implementation("io.ktor:ktor-client-mock:$ktorVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
        }
    }
}

/**
 * To generate/update schemas, use command:
 * .\gradlew entur:downloadApolloSchema --endpoint="https://api.entur.io/journey-planner/v3/graphql" --schema="entur/src/main/graphql/journeyplanner/schema.json"
 * .\gradlew entur:downloadApolloSchema --endpoint="https://api.entur.io/realtime/v1/vehicles/graphql" --schema="entur/src/main/graphql/vehiclepositions/schema.json"
 *
 */
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
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Martinsbl/entur-kotlin-client")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("github.actor") as String?
                password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("github.token") as String?
            }
        }
    }
}
