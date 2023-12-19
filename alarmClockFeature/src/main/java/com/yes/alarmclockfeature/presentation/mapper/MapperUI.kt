package com.yes.alarmclockfeature.presentation.mapper

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.AlarmItemUI
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import java.util.Calendar

class MapperUI {
    private val calendar = Calendar.getInstance()
    fun map(date: DatePickerManager.Time, repeating: Map<String, Boolean>): Alarm {
        calendar.set(Calendar.HOUR_OF_DAY, date.hour);
        calendar.set(Calendar.MINUTE, date.minute);
        return Alarm(
            date.hour,
            date.minute,
            repeating.getOrDefault("sun", false),
            repeating.getOrDefault("mon", false),
            repeating.getOrDefault("tue", false),
            repeating.getOrDefault("wed", false),
            repeating.getOrDefault("thu", false),
            repeating.getOrDefault("sun", false),
            repeating.getOrDefault("sat", false),
            true
        )
    }
}