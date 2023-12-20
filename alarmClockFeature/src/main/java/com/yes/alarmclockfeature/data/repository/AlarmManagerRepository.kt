package com.yes.alarmclockfeature.data.repository

import com.yes.alarmclockfeature.data.dataSource.AlarmDataSource
import com.yes.alarmclockfeature.domain.model.Alarm

class AlarmManagerRepository(
    private val alarmDataSource: AlarmDataSource
) {
    fun setAlarm(alarm: Alarm){
        alarmDataSource.setAlarm(alarm.timeHour,alarm.timeMinute)
    }
}