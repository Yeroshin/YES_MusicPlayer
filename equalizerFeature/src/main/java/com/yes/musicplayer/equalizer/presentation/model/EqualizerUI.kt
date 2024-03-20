package com.yes.musicplayer.equalizer.presentation.model

data class EqualizerUI(
    val equalizerEnabled: Boolean? = null,
    val currentPreset: Int? = null,
    val presetsNames: List<String>? = null,
    val bandsLevelRange:Int?=null,
    val equalizerValues:IntArray? = null,
    val equalizerValuesInfo:List<String>? = null,
    val loudnessEnhancerEnabled:Boolean?=null,
    val loudnessEnhancerValue:Int?=null
)
