# AGENTS.md — entur-kotlin-client

Guidance for coding agents working in this repo. End-user usage examples live in [README.md](README.md); this file is for orientation and conventions.

## Project overview

Kotlin Multiplatform library wrapping [Entur's](https://developer.entur.org/) GraphQL and REST APIs in a typed Kotlin API. Targets JVM, Android, and WasmJS.

- **Group / version**: `net.testiprod.entur` / `0.2.1-SNAPSHOT`
- **Published to**: `mavenLocal` and GitHub Packages (`Martinsbl/entur-kotlin-client`)

## Module layout

- [`entur/`](entur/) — the published KMP library
- [`test-app/`](test-app/) — JVM-only consumer used for manually exercising the library ([test-app/src/main/kotlin/Main.kt](test-app/src/main/kotlin/Main.kt))

## Source set layout under `entur/src/`

- `commonMain/kotlin/...` — all shared Kotlin code
- `commonMain/graphql/journeyplanner/` — schema, queries, fragments for the journey-planner Apollo service
- `commonMain/graphql/vehiclepositions/` — schema, queries, fragments for the vehicle-positions Apollo service
- `jvmMain/` — Ktor CIO engine, logback-classic
- `androidMain/` — Ktor OkHttp engine
- `wasmJsMain/` — Ktor JS engine
- `commonTest/` — shared test scaffolding
- `jvmTest/` — JVM tests; mock responses in `jvmTest/resources/mock_responses/`

## Package map under `net.testiprod.entur.*`

- `common.exceptions` — `EnturResponseException`, `EnturParseException`, `StopMonitorParseException`
- `common.models` — shared domain types: `Line`, `EstimatedCall`, `ServiceJourney`, `TransportMode`, `OccupancyStatus`, `Situation`, `DestinationDisplay`, etc.
- `common.serialization` — `InstantSerializer` (`kotlin.time.Instant`)
- `common` — `Mapping.kt`, `EnturConstants.kt`, `StopMonitorUtils.kt`
- `http` — `EnturApolloClientFactory`, `EnturHttpClientFactory`, `EnturResult<T>`, `HttpRequestManager`
- `geocoder` — REST stop-place search (`GeocoderApi`)
- `journeyplanner.stopplace.api` — `StopPlaceApi` / `IStopPlaceApi` (GraphQL)
- `journeyplanner.stopplace.service` — `StopPlaceService` (Flow)
- `journeyplanner.stopplace.filtering` — `EstimatedCallFilter`, `LineFilter`, `LineDirectionFilter`
- `journeyplanner.stopplace.models` — `Quay`, `StopPlaceDetails`, `StopPlaceQuay`
- `journeyplanner.trip` — `TripApi` / `ITripApi`, models, `TripMapping`
- `vehicle.api` — `VehicleApi` (query + WebSocket subscription)
- `vehicle.service` — `VehicleService` / `IVehicleService` (Flow, merge + filter)
- `vehicle.models` — `Vehicle`, `Location`
- `mocks` — `Mocks.kt`, `EstimatedCallMocks.kt`
- `apollographql.journeyplanner` / `apollographql.vehiclepositions` — **generated, do not edit** (also excluded from ktlint and the IntelliJ formatter, see `.editorconfig`)

## Build / test / lint

```bash
# Build everything
./gradlew build

# Run all tests (CI uses --continue)
./gradlew test

# Tests for one module / class / method
./gradlew :entur:test
./gradlew :entur:jvmTest --tests "net.testiprod.entur.geocoder.GeocoderApiTest"
./gradlew :entur:jvmTest --tests "net.testiprod.entur.geocoder.GeocoderApiTest.fetchStopPlaces"

# Lint
./gradlew ktlintCheck
./gradlew ktlintFormat

# Full check (build + tests + lint)
./gradlew check

# Publish locally (for trying out in another project via mavenLocal)
./gradlew publishToMavenLocal
```

### Refreshing GraphQL schemas

Both Apollo `srcDir`s live under `src/commonMain/graphql/<service>/`. Use:

```bash
./gradlew entur:downloadApolloSchema \
  --endpoint="https://api.entur.io/journey-planner/v3/graphql" \
  --schema="entur/src/commonMain/graphql/journeyplanner/schema.json"

./gradlew entur:downloadApolloSchema \
  --endpoint="https://api.entur.io/realtime/v2/vehicles/graphql" \
  --schema="entur/src/commonMain/graphql/vehiclepositions/schema.json"
```

## GraphQL

Two Apollo services are configured in [entur/build.gradle.kts](entur/build.gradle.kts), both with `generateAsInternal = true`:

| Service | Endpoint | Generated package |
| --- | --- | --- |
| `journeyplanner` | `https://api.entur.io/journey-planner/v3/graphql` | `net.testiprod.entur.apollographql.journeyplanner` |
| `vehiclepositions` | `https://api.entur.io/realtime/v2/vehicles/graphql` | `net.testiprod.entur.apollographql.vehiclepositions` |

- Queries and fragments live alongside the schema in `commonMain/graphql/<service>/`.
- Prefer adding fragments to keep queries composable (see existing `EstimatedCallFragment`, `LineFragment`, `ServiceJourneyFragment`, `VehicleUpdateFragment`).
- Never edit generated sources under `apollographql/**`.

## HTTP and Apollo client construction

- `EnturHttpClientFactory` builds Ktor `HttpClient`s; it has a WebSocket-enabled variant used by vehicle subscriptions.
- `EnturApolloClientFactory` builds Apollo clients for both services.
- Every API factory takes `companyName` and `appName`; these are concatenated into the **mandatory** `ET-Client-Name: <companyName>-<appName>` header that Entur requires on all requests.
- Per-platform Ktor engines: CIO (JVM), OkHttp (Android), JS (WasmJS).

## Result and error handling

- Public APIs return `EnturResult<T>` (sealed) with `Success(data)` and `Error(exception)` variants.
- Wrap Apollo / Ktor failures in `EnturResponseException`.
- Throw `EnturParseException` (or the more specific `StopMonitorParseException`) immediately on unrecoverable parse errors.
- For long-lived subscriptions, use coroutine `retryWhen` with exponential backoff (see WebSocket retry in `VehicleApi`).

## Flow / subscription pattern

Subscriptions combine an initial query with a long-lived feed:

```kotlin
merge(initialQuery, subscription)
    .flowOn(Dispatchers.Default)
    .filter { it.isNotEmpty() }
    .map { filterVehicles(it) }
```

Reference: [VehicleService.kt:20](entur/src/commonMain/kotlin/net/testiprod/entur/vehicle/service/VehicleService.kt:20).

WebSocket details for vehicle positions:
- Endpoint: `wss://api.entur.io/realtime/v2/vehicles/subscriptions`
- Protocol: GraphQL-WS
- Retry: exponential backoff, capped at 30 s
- Idle timeout: 60 s

## Code style

Driven by [.editorconfig](.editorconfig) and ktlint.

- 4-space indent, max line length 120
- UTF-8 with LF endings, final newline required
- No wildcard imports
- Naming: `PascalCase` types, `camelCase` functions and variables, `UPPER_SNAKE_CASE` constants, `is/can/has` for booleans
- `apollographql/**` and back-ticked identifiers are excluded from ktlint
- Run `./gradlew ktlintFormat` before committing

## Serialization

- Kotlinx Serialization with `ignoreUnknownKeys = true`
- `InstantSerializer` for `kotlin.time.Instant`
- Keep custom serializers in `common/serialization/` and route them through shared JSON config

## Testing

- Tests in `entur/src/jvmTest/` (or `commonTest/` when fully shared)
- Use `runTest { }` from `kotlinx-coroutines-test`
- Mock HTTP with Ktor `MockEngine`; JSON fixtures in `jvmTest/resources/mock_responses/` (read via `getResourceFileAsText()`)
- Cover both success and error paths

## CI

[.github/workflows/test.yml](.github/workflows/test.yml): on push to master and PRs, runs `./gradlew test --continue` on Ubuntu + JDK 17 with the Gradle cache.

## Publishing

`maven-publish` plugin in [entur/build.gradle.kts](entur/build.gradle.kts) targets:

- `mavenLocal`
- GitHub Packages: `https://maven.pkg.github.com/Martinsbl/entur-kotlin-client`
  - Credentials: `GITHUB_ACTOR` / `GITHUB_TOKEN` env vars, or `github.actor` / `github.token` Gradle properties

## Versions

- Kotlin **2.3.0**
- Ktor **3.4.3**
- Apollo **4.4.1** + `apollo-engine-ktor` 0.1.1
- kotlinx-coroutines **1.10.2**
- kotlinx-datetime **0.7.1**
- ktlint plugin **14.0.1**
- JVM toolchain **17**, Android compileSdk **36**, minSdk **33**

## Files an agent should know

- [entur/build.gradle.kts](entur/build.gradle.kts) — KMP targets, Apollo services, publishing
- [entur/src/commonMain/kotlin/net/testiprod/entur/http/](entur/src/commonMain/kotlin/net/testiprod/entur/http/) — client factories, `EnturResult`, `HttpRequestManager`
- [entur/src/commonMain/kotlin/net/testiprod/entur/vehicle/service/VehicleService.kt](entur/src/commonMain/kotlin/net/testiprod/entur/vehicle/service/VehicleService.kt) — reference for Flow + subscription patterns
- [entur/src/commonMain/graphql/](entur/src/commonMain/graphql/) — schemas, queries, fragments
- [.editorconfig](.editorconfig) — formatter and ktlint rules
- [.github/workflows/test.yml](.github/workflows/test.yml) — CI

## Workflow checklist

1. Make changes
2. `./gradlew ktlintFormat`
3. `./gradlew build`
4. Add / update tests
5. `./gradlew test`
