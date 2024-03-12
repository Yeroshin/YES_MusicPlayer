package com.yes.alarmclockfeature.presentation

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import java.util.Calendar

object MapperUiFixtures {
    private val alarms = listOf(
        Alarm(
            1,
            14,
            30,
            setOf(Calendar.SUNDAY),
            true
        ),
        Alarm(
            2,
            14,
            30,
            setOf(Calendar.MONDAY),
            true
        ),
        Alarm(
            3,
            14,
            15,
            setOf(Calendar.TUESDAY),
            true
        ),
        Alarm(
            3,
            14,
            30,
            setOf(Calendar.WEDNESDAY),
            false
        ),
        Alarm(
            3,
            2,
            30,
            setOf(Calendar.THURSDAY),
            false
        ),
        Alarm(
            3,
            2,
            30,
            setOf(Calendar.FRIDAY),
            true
        ),
        Alarm(
            3,
            2,
            30,
            setOf(Calendar.SATURDAY),
            false
        ),
        Alarm(
            3,
            1,
            30,
            setOf(),
            false
        )
    )
    fun getSundayAlarm():Alarm{
        return Alarm(
            1,
            12,
            0,
            setOf(Calendar.TUESDAY),
            false
        )
    }
    fun getSundayAlarmUI(): AlarmUI {
        return AlarmUI(
            1,
            "12",
            "30",
            "256",
            setOf(),
            false
        )
    }
    fun getCurrentDaySunday():Int{
        return Calendar.MONDAY
    }
    fun getCurrentHour():Int{
        return 12
    }
    fun getCurrentMinute():Int{
        return 0
    }
}