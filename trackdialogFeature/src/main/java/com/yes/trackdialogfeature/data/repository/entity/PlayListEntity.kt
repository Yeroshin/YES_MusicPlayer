package com.yes.trackdialogfeature.data.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "playlists")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
    val currentTrack: Int,
    val currentTrackPosition: Int,
)
