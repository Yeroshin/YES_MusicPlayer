package com.yes.musicplayer.equalizer.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI

class EqualizerContract {

    sealed class Event : UiEvent {
        data class OnPresetSelected(val preset:Short): Event()
        data object OnPlay : Event()
        data class OnEqualizerValue(val band:Int,val value:Int,val maxLevelRange: Int): Event()

    }

    data class State(
        val state: EqualizerState
    ) : UiState


    sealed class EqualizerState {
        data class Success(val equalizer: EqualizerUI):EqualizerState()
        data object Idle : EqualizerState()

    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}