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
    fun getSundayAlarmUI(): AlarmUI {
        return AlarmUI(
            1,
            "12:10",
            "0",
            "10",
            setOf(),
            false
        )
    }
    fun getSundayAlarm():Alarm{
        return Alarm(
            1,
            12,
            10,
            setOf(),
            false
        )
    }

    fun getCurrentDaySunday():Int{
        return Calendar.WEDNESDAY
    }
    fun getCurrentHour():Int{
        return 12
    }
    fun getCurrentMinute():Int{
        return 0
    }
}