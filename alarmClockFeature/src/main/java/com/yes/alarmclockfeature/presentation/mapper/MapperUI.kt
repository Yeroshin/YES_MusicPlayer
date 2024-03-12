package com.yes.alarmclockfeature.presentation.mapper

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MapperUI(
    private val calendar: Calendar
) {

    fun map(date: DatePickerManager.Time, selectedDays:Set<Int>): Alarm {
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
        val currentTime = calendar.get(Calendar.HOUR) * 60 + calendar.get(Calendar.MINUTE)
        var nearestTimeDiff = Int.MAX_VALUE
        val alarmCopy=if(alarm.daysOfWeek== emptySet<Int>()){
            alarm.copy(daysOfWeek = setOf(currentDayOfWeek))
        }else{
            alarm
        }
        alarmCopy.daysOfWeek.forEach{dayOfWeek->
            val minutesUntilNext: Int = ((dayOfWeek - currentDayOfWeek + 7) % 7) * 24 * 60+(alarm.timeHour*60+alarm.timeMinute)
            val diff = minutesUntilNext - currentTime
            if (diff < nearestTimeDiff) {
                nearestTimeDiff = diff
            }
        }
        val timeHour=nearestTimeDiff/60
        val timeMinute=nearestTimeDiff/60

        ///////////////////////////////
        val currentTimeMinutes = getCurrentTimeInMinutes()
        val alarmTimeMinutes = calculateMinutesUntilNextAlarm(alarm, currentTimeMinutes)

        val minutesLeftTotal = calculateMinutesLeft(currentTimeMinutes, alarmTimeMinutes)
        val (hoursLeft, minutesLeft) = convertMinutesToHoursAndMinutes(minutesLeftTotal)

        return AlarmUI(
            alarm.id,
            formatTime(alarm.timeHour, alarm.timeMinute),
            hoursLeft.toString(),
            minutesLeft.toString(),
            alarm.daysOfWeek ?: emptySet(),
            alarm.enabled
        )
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
                minutesUntilNextAlarm = calculateMinutesLeft(currentTimeMinutes, alarmTimeForNextDay)
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