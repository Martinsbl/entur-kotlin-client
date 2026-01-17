# Entur Kotlin Client

A Kotlin Multiplatform client for the [Entur](https://entur.no) public transport API. Supports JVM, Android, and Wasm/JS.

## Features

- **Stop Place API**: Fetch departures and stop place details
- **Vehicle API**: Real-time vehicle positions with subscription support
- **Geocoder API**: Search for stop places by name

## Tech Stack

- Kotlin Multiplatform (JVM, Android, WasmJS)
- Apollo GraphQL
- Ktor HTTP Client
- Kotlinx Coroutines & Flows
- Kotlinx Serialization

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("net.testiprod.entur:entur:last_version")
}
```

## Usage

### Stop Place Departures

```kotlin
val stopPlaceApi = StopPlaceApi(
    companyName = "your-company",
    appName = "your-app"
)

// Fetch departures
val result = stopPlaceApi.fetchStopPlaceQuay("NSR:StopPlace:337")
when (result) {
    is EnturResult.Success -> println(result.data.estimatedCalls)
    is EnturResult.Error -> println(result.exception)
}
```

### Observe Stop Place (Flow)

```kotlin
val stopPlaceService = StopPlaceService(stopPlaceApi)

stopPlaceService.observeStopPlace(
    stopPlaceId = "NSR:StopPlace:337",
    numberOfDepartures = 10,
    refreshInterval = 30.seconds
).collect { result ->
    // Handle updates
}
```

### Vehicle Positions

```kotlin
val vehicleApi = VehicleApi(
    companyName = "your-company",
    appName = "your-app"
)

// One-time fetch
val vehicles = vehicleApi.fetchVehicles(codeSpaceId = "SKY", lineRef = null)

// Subscribe to updates
vehicleApi.subscribeToVehicleUpdates("SKY", null) { error, attempt ->
    println("Retry $attempt: $error")
}.collect { vehicles ->
    // Handle vehicle updates
}
```

### Search Stop Places

```kotlin
val httpClient = EnturHttpClientFactory.create("your-company", "your-app")
val geocoderApi = GeocoderApi(httpClient)

val result = geocoderApi.fetchStopPlaces("Oslo S")
```

## Resources

- [Journey Planner v3 API](https://developer.entur.org/pages-journeyplanner-journeyplanner)
- [Journey Planner v3 Explorer](https://api.entur.io/graphql-explorer/journey-planner-v3)
- [Vehicle Position Explorer](https://api.entur.io/graphql-explorer/vehicles)
- [Geocoder API](https://developer.entur.org/pages-geocoder-intro)
