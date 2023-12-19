package com.yes.alarmclockfeature.data.repository

import com.yes.alarmclockfeature.data.mapper.Mapper
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.core.domain.repository.IAlarmDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmListRepository(
    private val mapper: Mapper,
    private val alarmDao: IAlarmDao
) {
    fun addAlarm(alarm: Alarm):Long{
        return alarmDao.saveAlarm(
            mapper.map(alarm)
        )
    }
    fun subscribeAlarms(): Flow<List<Alarm>> {
        return alarmDao.subscribeAlarms().map {entity->
            entity.map {
                mapper.map(it)
            }
        }
    }
}