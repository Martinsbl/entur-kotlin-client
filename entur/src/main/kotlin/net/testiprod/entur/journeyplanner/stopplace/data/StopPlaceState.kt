package net.testiprod.entur.journeyplanner.stopplace.data

sealed class EnturState<out T> {
    data object Loading : EnturState<Nothing>()
    data class Data<out T>(val stopPlace: T) : EnturState<T>()
}
