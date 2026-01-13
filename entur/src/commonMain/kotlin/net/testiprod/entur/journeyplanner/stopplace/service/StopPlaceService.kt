package net.testiprod.entur.journeyplanner.stopplace.service

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.testiprod.entur.http.EnturResult
import net.testiprod.entur.journeyplanner.stopplace.client.IStopPlaceApi
import net.testiprod.entur.journeyplanner.stopplace.models.StopPlaceQuay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class StopPlaceService(private val stopPlaceApi: IStopPlaceApi) {
    fun observeStopPlace(
        stopPlaceId: String,
        numberOfDepartures: Int = 10,
        whiteListedLines: List<String>? = null,
        refreshInterval: Duration = 30.seconds,
    ): Flow<EnturResult<StopPlaceQuay>> {
        checkConfiguration(refreshInterval)
        return flow {
            while (true) {
                val result = try {
                    stopPlaceApi.fetchStopPlaceQuay(
                        stopPlaceId,
                        numberOfDepartures,
                        whiteListedLines,
                    )
                } catch (e: Exception) {
                    EnturResult.Error(e)
                }
                emit(result)
                delay(refreshInterval)
            }
        }
    }

    private fun checkConfiguration(refreshInterval: Duration) {
        check(refreshInterval.inWholeMilliseconds > 15_000L) {
            "Refresh rate is set too low ($refreshInterval)"
        }
    }
}
