package com.yes.musicplayer.equalizer.domain.entity

data class Equalizer (
    val equalizerEnabled:Boolean?=null,
    val currentPreset:Int?=null,
    val presetsNames:List<String>?=null,
    val bandsLevelRanges:List<IntArray>?=null,
    val equalizerValues:List<Short>?=null,
)