package net.testiprod.entur.journeyplanner.timetableconfig.data

import net.testiprod.entur.journeyplanner.timetableconfig.TimeTableConfiguration

interface TimeTableConfigurationRepo {
    fun getTimeTables(): Array<TimeTableConfiguration>
}
