package com.yes.core.domain.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yes.core.data.entity.AlarmDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IAlarmDao {
    @Insert
    fun saveAlarm(alarm: AlarmDataBaseEntity): Long
    @Query("SELECT * FROM alarms")
    fun subscribeAlarms(): Flow<List<AlarmDataBaseEntity>>
}