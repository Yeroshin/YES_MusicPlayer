package com.yes.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "playlists")
data class PlayListDataBaseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
)
