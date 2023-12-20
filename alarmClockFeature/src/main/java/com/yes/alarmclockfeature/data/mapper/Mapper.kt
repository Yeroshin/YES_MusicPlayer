package com.yes.alarmclockfeature.data.mapper

import android.widget.ToggleButton
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.core.data.entity.AlarmDataBaseEntity

class Mapper {
    fun map(alarm: Alarm): AlarmDataBaseEntity {


        return AlarmDataBaseEntity(
            alarm.id,
            alarm.timeHour,
            alarm.timeMinute,
            alarm.daysOfWeek.contains(DayOfWeek.SUNDAY),
            alarm.daysOfWeek.contains(DayOfWeek.MONDAY),
            alarm.daysOfWeek.contains(DayOfWeek.TUESDAY),
            alarm.daysOfWeek.contains(DayOfWeek.WEDNESDAY),
            alarm.daysOfWeek.contains(DayOfWeek.THURSDAY),
            alarm.daysOfWeek.contains(DayOfWeek.FRIDAY),
            alarm.daysOfWeek.contains(DayOfWeek.SATURDAY),
            alarm.enabled,
        )
    }
    fun map(alarm:AlarmDataBaseEntity ): Alarm {
        val days: List<Boolean> = listOf(
            alarm.sun,
            alarm.mon,
            alarm.tue,
            alarm.wed,
            alarm.thu,
            alarm.fri,
            alarm.sat
        )
        val selectedDays: MutableSet<DayOfWeek> = mutableSetOf()
        for ((index, day) in days.withIndex()) {
            if (day) {
                when (index) {
                    0 -> selectedDays.add(DayOfWeek.SUNDAY)
                    1 -> selectedDays.add(DayOfWeek.MONDAY)
                    2 -> selectedDays.add(DayOfWeek.TUESDAY)
                    3 -> selectedDays.add(DayOfWeek.WEDNESDAY)
                    4 -> selectedDays.add(DayOfWeek.THURSDAY)
                    5 -> selectedDays.add(DayOfWeek.FRIDAY)
                    6 -> selectedDays.add(DayOfWeek.SATURDAY)
                }
            }
        }
        return Alarm(
            alarm.id?:0,
            alarm.hour,
            alarm.minute,
            selectedDays,
            alarm.enabled,
        )
    }
}