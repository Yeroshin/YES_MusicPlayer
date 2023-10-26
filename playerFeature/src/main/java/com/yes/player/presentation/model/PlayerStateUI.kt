package com.yes.player.presentation.model

data class PlayerStateUI(
    val playListName:String?=null,
    val trackTitle:String?=null,
    val durationCounter:String?=null,
    val duration:String?=null,
    val durationInt:Int?=null,
    val stateBuffering:Boolean=false,
    val progress:Int?=null
)