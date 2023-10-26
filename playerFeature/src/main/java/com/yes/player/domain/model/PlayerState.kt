package com.yes.player.domain.model

data class PlayerState (
    val albumTitle:String?,
    val artist:String?,
    val title:String?,
    val stateBuffering:Boolean,
    val duration:Long?
)