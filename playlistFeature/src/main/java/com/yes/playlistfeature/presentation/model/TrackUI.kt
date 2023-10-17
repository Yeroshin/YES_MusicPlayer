package com.yes.playlistfeature.presentation.model

data class TrackUI (
    val id:Long,
    val title:String,
    val info:String,
    val duration:String,
    val current:Boolean=false,
    var selected:Boolean=false
)