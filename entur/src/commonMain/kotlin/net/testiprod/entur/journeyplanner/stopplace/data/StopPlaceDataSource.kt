package net.testiprod.entur.journeyplanner.stopplace.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.client.IStopPlaceApi
import net.testiprod.entur.journeyplanner.timetableconfig.TimeTableConfiguration

class StopPlaceDataSource(private val stopPlaceApi: IStopPlaceApi) {

    fun getStopPlaceStateFlow(configuration: TimeTableConfiguration): Flow<EnturState<StopPlaceQuay>> {
        checkConfiguration(configuration)
        return flow {
            while (true) {
                emit(EnturState.Loading)
                val enturResponse = stopPlaceApi.fetchStopPlaceQuay(
                    configuration.stopId,
                    configuration.numberOfDepartures,
                    configuration.directionType,
                    configuration.whitelistedLines,
                )
                if (enturResponse is EnturResult.Success) {
                    emit(EnturState.Data(enturResponse.data))
                }
                delay(configuration.refreshRateMs)
            }
        }
    }

    fun getQuayFlow(configuration: TimeTableConfiguration): Flow<EnturState<StopPlaceQuay>> {
        checkConfiguration(configuration)
        return flow {
            while (true) {
                emit(EnturState.Loading)
                val enturResponse = stopPlaceApi.fetchQuay(
                    configuration.stopId,
                    configuration.numberOfDepartures,
                    configuration.directionType,
                    configuration.whitelistedLines,
                )
                if (enturResponse is EnturResult.Success) {
                    emit(EnturState.Data(enturResponse.data))
                }
                delay(configuration.refreshRateMs)
            }
        }
    }

    private fun checkConfiguration(configuration: TimeTableConfiguration) {
        check(configuration.refreshRateMs > 15_000L) {
            "Refresh rate is set too low (${configuration.refreshRateMs} ms)"
        }
        check(configuration.numberOfDepartures > 0) {
            "Invalid number of departures (${configuration.numberOfDepartures})"
        }
    }
}
