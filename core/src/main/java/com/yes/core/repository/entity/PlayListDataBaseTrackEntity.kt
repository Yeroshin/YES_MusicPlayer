package com.yes.core.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tracks")
data class PlayListDataBaseTrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?=null,
    val playlistId: Long,
    val artist: String="",
    val title: String,
    val uri: String,
    val duration: Long=-1,
    val album:String="",
    val size:Long=-1,
    val position:Int
)