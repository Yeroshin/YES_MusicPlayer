package com.yes.alarmclockfeature.presentation.mapper

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import java.util.Calendar

class MapperUI(
    private val calendar: Calendar
) {

    fun map(date: DatePickerManager.Time, selectedDays: Set<Int>): Alarm {
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
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val currentTimeMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        var nearestTimeDiff = Int.MAX_VALUE
        val daysOfWeek = alarm.daysOfWeek.ifEmpty { setOf(currentDayOfWeek) }
        daysOfWeek.forEach { dayOfWeek ->
            val minutesUntilNext = calculateMinutesUntilNextAlarm(
                dayOfWeek,
                currentDayOfWeek,
                alarm.timeHour,
                alarm.timeMinute,
                currentTimeMinutes
            )
            val diff = if (minutesUntilNext < 0){
                minutesUntilNext + 24 * 60
            } else minutesUntilNext
            if (diff < nearestTimeDiff) {
                nearestTimeDiff = diff
            }
        }
        return AlarmUI(
            alarm.id,
            formatTime(alarm.timeHour, alarm.timeMinute),
            (nearestTimeDiff / 60).toString(),
            (nearestTimeDiff % 60).toString(),
            alarm.daysOfWeek,
            alarm.enabled
        )
    }
    private fun calculateMinutesUntilNextAlarm(dayOfWeek: Int, currentDayOfWeek: Int, timeHour: Int, timeMinute: Int, currentTimeMinutes: Int): Int {
        val daysDiff = (dayOfWeek - currentDayOfWeek + 7) % 7
        return daysDiff * 24 * 60 + (timeHour * 60 + timeMinute) - currentTimeMinutes
    }

    private fun calculateMinutesUntilNextAlarm(alarm: Alarm, currentTimeMinutes: Int): Int {
        val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val alarmTimeInMinutes = convertTimeToMinutes(alarm)

        var daysToAdd = 0
        var minutesUntilNextAlarm = 0

        val daysOfWeekValues = alarm.daysOfWeek.map { it } ?: emptyList()

        /* if (daysOfWeekValues.isEmpty()) {
             return -1 // Возвращаем -1 в случае пустого списка daysOfWeek
         }*/
        if (daysOfWeekValues.isEmpty()) {
            return calculateMinutesLeft(currentTimeMinutes, alarmTimeInMinutes)
        }

        while (true) {
            val nextDay = (currentDayOfWeek + daysToAdd) % 7 + 1
            if (daysOfWeekValues.contains(nextDay)) {
                val alarmTimeForNextDay = alarmTimeInMinutes + daysToAdd * 24 * 60
                minutesUntilNextAlarm =
                    calculateMinutesLeft(currentTimeMinutes, alarmTimeForNextDay)
                break
            }
            daysToAdd++
        }

        return minutesUntilNextAlarm
    }

    private fun convertTimeToMinutes(alarm: Alarm): Int {
        return alarm.timeHour * 60 + alarm.timeMinute
    }

    private fun getCurrentTimeInMinutes(): Int {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        return hour * 60 + min
    }

    private fun calculateMinutesLeft(currentTimeMinutes: Int, alarmTimeMinutes: Int): Int {
        var minutesLeftTotal = alarmTimeMinutes - currentTimeMinutes
        if (minutesLeftTotal < 0) {
            minutesLeftTotal = (alarmTimeMinutes + 24 * 60) - currentTimeMinutes
        }
        return minutesLeftTotal
    }

    private fun convertMinutesToHoursAndMinutes(totalMinutes: Int): Pair<Int, Int> {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return Pair(hours, minutes)
    }

    private fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }
}