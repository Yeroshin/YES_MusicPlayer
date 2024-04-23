package com.yes.alarmclockfeature.data.repository

import com.yes.alarmclockfeature.data.dataSource.AlarmDataSource
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.DayOfWeek

class AlarmManagerRepository(
    private val alarmDataSource: AlarmDataSource
) {
    fun setAlarm(alarm: Alarm,dayOfWeek: Int){
        alarmDataSource.setAlarm(alarm.id,alarm.timeHour,alarm.timeMinute,dayOfWeek)
    }
    fun cancelAlarm(){
        alarmDataSource.cancelAlarm()
    }
}