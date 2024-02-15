package com.yes.musicplayer.equalizer.presentation.model

data class EqualizerUI(
    val equalizerEnabled:Boolean?=null,
    val equalizerValues:List<Int>?=null,
    val currentPreset:Int?=null,
    val presets:List<String>?=null
)
