package com.yes.musicplayer.equalizer.domain.entity

import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract

data class Equalizer (
    val equalizerEnabled:Boolean?=null,
    val currentPreset:Int?=null,
    val presetsNames:List<String>?=null,
    val bandsLevelRange:IntArray?=null,
    val equalizerValues:List<Int>?=null,
    val loudnessEnhancerEnabled:Boolean?=null,
    val loudnessEnhancerValue:Int?=null
)