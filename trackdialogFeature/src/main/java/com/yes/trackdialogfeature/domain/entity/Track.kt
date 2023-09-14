package com.yes.trackdialogfeature.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey(autoGenerate = true)
    val id: Long?=null,
    val playlistName: String,
    val artist: String="",
    val title: String,
    val uri: String,
    val duration: Long=-1,
    val album:String="",
    val size:Long=-1,
)