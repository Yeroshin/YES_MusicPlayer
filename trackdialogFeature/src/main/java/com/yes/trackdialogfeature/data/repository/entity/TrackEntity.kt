package com.yes.trackdialogfeature.data.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey(autoGenerate = true)
    val trackId: Int?,
    val playlistId: Int?,
    val artist: String,
    val title: String,
    val uri: String,
    val duration: Int,
)