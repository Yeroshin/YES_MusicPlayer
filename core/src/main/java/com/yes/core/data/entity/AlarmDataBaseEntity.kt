package com.yes.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmDataBaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val hour:Int,
    val minute:Int,
    val sun:Boolean,
    val mon:Boolean,
    val tue:Boolean,
    val wed:Boolean,
    val thu:Boolean,
    val fri:Boolean,
    val sat:Boolean,
    val enabled:Boolean
)