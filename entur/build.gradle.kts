plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.3.0"
    id("com.apollographql.apollo").version("4.3.3")
    `java-library`
    `maven-publish`
}

group = "net.testiprod.entur"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.apollographql.ktor:apollo-engine-ktor:0.1.1")
    implementation("com.apollographql.apollo:apollo-runtime:4.3.3")
    implementation("io.ktor:ktor-client-cio-jvm:3.3.3")

    val ktorVersion = "3.3.3"
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("joda-time:joda-time:2.10.14") // For java data replacement and desktop compatibility
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

/**
 * To generate/update schemas, use command:
 * .\gradlew entur:downloadApolloSchema --endpoint="https://api.entur.io/journey-planner/v3/graphql" --schema="entur/src/main/graphql/journeyplanner/schema.json"
 * .\gradlew entur:downloadApolloSchema --endpoint="https://api.entur.io/realtime/v1/vehicles/graphql" --schema="entur/src/main/graphql/vehiclepositions/schema.json"
 *
 */
apollo {
    service("journeyplanner") {
        packageName.set("net.testiprod.entur.apollographql.journeyplanner.")
        srcDir("src/main/graphql/journeyplanner")
        generateAsInternal.set(true)
    }

    service("vehiclepositions") {
        packageName.set("net.testiprod.entur.apollographql.vehiclepositions.")
        srcDir("src/main/graphql/vehiclepositions")
        generateAsInternal.set(true)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "client"
            version = project.version.toString()
        }
    }

    repositories {
        mavenLocal()

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/martinsbl/entur-kotlin-client")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: findProperty("github.actor") as String?
                password = System.getenv("GITHUB_TOKEN") ?: findProperty("github.token") as String?
            }
        }
    }
}
