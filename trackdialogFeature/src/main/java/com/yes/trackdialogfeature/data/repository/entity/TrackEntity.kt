package com.yes.trackdialogfeature.data.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val playlistName: String,
    val artist: String,
    val title: String,
    val uri: String,
    val duration: Long,
    val album:String,
    val size:Long,
)