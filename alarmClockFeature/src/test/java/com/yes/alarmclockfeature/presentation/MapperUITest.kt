package com.yes.alarmclockfeature.presentation

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.Calendar


internal class MapperUITest {
    private val calendar: Calendar = mockk()
    private val cut= MapperUI(calendar)



    @ParameterizedTest
    @MethodSource("runData")
    fun run(
        param: Alarm,
        expected: AlarmUI,
        currentDay: Int,
        currentHour:Int,
        currentMinute:Int
    )  {
        every {
            calendar.get(Calendar.DAY_OF_WEEK)
        } returns currentDay
        every {
            calendar.get(Calendar.HOUR)
        } returns currentHour
        every {
            calendar.get(Calendar.MINUTE)
        } returns currentMinute
        val actual = cut.map(param)
        // Assert
        assert(expected == actual)
    }


    companion object {
        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MapperUiFixtures.getSundayAlarm(),
                    MapperUiFixtures.getSundayAlarmUI(),
                    MapperUiFixtures.getCurrentDaySunday(),
                    MapperUiFixtures.getCurrentHour(),
                    MapperUiFixtures.getCurrentMinute()
                ),

            )
        }
    }


}