package com.yes.alarmclockfeature.presentation.mapper

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager

class MapperUI {
    fun map(date: DatePickerManager.Time, selectedDays:Set<DayOfWeek>): Alarm {
        return Alarm(
            null,
            date.hour,
            date.minute,
            selectedDays,
            true
        )
    }
    fun map(alarm: AlarmUI): Alarm {
        val parts = alarm.alarmTime.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        return Alarm(
            alarm.id,
            hour,
            minute,
            alarm.daysOfWeek,
            alarm.enabled
        )
    }
    fun map(alarm: Alarm): AlarmUI {
        return AlarmUI(
            alarm.id,
            alarm.timeHour.toString() + ":" + alarm.timeMinute.toString(),
            "alarm.timeMinute: Int",
            alarm.daysOfWeek,
            alarm.enabled
        )
    }
}