package com.yes.musicplayer.equalizer.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI

class EqualizerContract {

    sealed class Event : UiEvent {
        data class OnPresetSelected(val preset:Int): Event()
        data class OnEqualizerEnabled(val enabled:Boolean): Event()
        data class OnEqualizerValue(val band:Int,val value:Int,val maxLevelRange: Int,val seekBarValues:IntArray): Event()
        data class OnEqualizerValueSet(val band:Int,val value:Int,val maxLevelRange: Int,val seekBarValues:IntArray): Event()

        data class OnLoudnessEnhancerEnabled(val enabled:Boolean): Event()
        data class OnLoudnessEnhancerTargetGain(val percent:Int): Event()
        data class OnLoudnessEnhancerTargetGainSet(val percent:Int): Event()

    }

    data class State(
        val state: EqualizerState,
        val equalizerEnabled: Boolean? = null,
        val currentPreset: Int? = null,
        val presetsNames: List<String>? = null,
        val bandsLevelRange:Int?=null,
        val equalizerValues:IntArray? = null,
        val equalizerValuesInfo:List<String>? = null,
        val loudnessEnhancerEnabled:Boolean?=null,
        val loudnessEnhancerValue:Int?=null
    ) : UiState


    sealed class EqualizerState {
        data object Success:EqualizerState()
        data object Idle : EqualizerState()

    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}