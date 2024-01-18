package com.yes.playlistfeature.domain.entity

data class Track(
    val id:Long,
    val playlistId: Long,
    val artist: String,
    val title: String,
    val uri: String,
    val duration: Long,
    val album:String,
    val size:Long,
    val position:Int,
)