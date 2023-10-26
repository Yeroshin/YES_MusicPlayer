package com.yes.player.presentation.model

data class PlayerStateUI(
    val playListName:String?=null,
    val trackTitle:String?=null,
    val durationCounter:String?=null,
    val duration:String?=null,
    val stateBuffering:Boolean=false
)