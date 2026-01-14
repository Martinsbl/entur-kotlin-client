package net.testiprod.entur.common.models

import kotlinx.serialization.Serializable

@Serializable
data class FeatureResult(val features: List<Feature>)

@Serializable
data class Feature(val properties: Properties)

@Serializable
data class Properties(
    val id: String,
    val name: String,
    val county: String,
    val locality: String,
    val label: String,
    val layer: String,
    val category: List<Category>,
)

enum class Category {
    airport,
    busStation,
    coachStation,
    ferryPort,
    ferryStop,
    harbourPort,
    liftStation,
    metroStation,
    onstreetBus,
    onstreetTram,
    railStation,
    tramStation,
    vehicleRailInterchange,
    GroupOfStopPlaces,
    other,
}
