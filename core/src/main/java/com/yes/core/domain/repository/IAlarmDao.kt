package com.yes.core.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yes.core.data.entity.AlarmDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IAlarmDao {
    @Insert
    fun saveAlarm(alarm: AlarmDataBaseEntity): Long
    @Query("SELECT * FROM alarms")
    fun subscribeAlarms(): Flow<List<AlarmDataBaseEntity>>
    @Delete
    fun deleteAlarm(alarm: AlarmDataBaseEntity):Int
    @Update
    fun updateAlarm(alarm: AlarmDataBaseEntity):Int


}