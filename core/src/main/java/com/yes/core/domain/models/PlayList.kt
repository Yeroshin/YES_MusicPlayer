package com.yes.core.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "playlists")
data class PlayList(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
)
