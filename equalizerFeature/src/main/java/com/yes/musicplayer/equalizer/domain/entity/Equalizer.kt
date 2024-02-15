package com.yes.musicplayer.equalizer.domain.entity

data class Equalizer (
    val enabled:Boolean?=null,
    val presetNames:List<String>?=null,
    val currentPreset:Int?=null
)