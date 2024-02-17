package com.yes.musicplayer.equalizer.presentation.model

data class EqualizerUI(
    val equalizerEnabled: Boolean? = null,
    val currentPreset: Int? = null,
    val presetsNames: List<String>? = null,
    val equalizerValues: List<Int>? = null,
)
