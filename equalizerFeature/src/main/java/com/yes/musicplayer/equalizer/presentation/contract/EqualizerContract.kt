package com.yes.musicplayer.equalizer.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI

class EqualizerContract {

    sealed class Event : UiEvent {
        data class OnPresetSelected(val preset:Short): Event()
        data class OnSeek(val position:Int) : Event()
        data object OnPlay : Event()

    }

    data class State(
        val state: EqualizerState
    ) : UiState


    sealed class EqualizerState {
        data class Init(val equalizer: EqualizerUI):EqualizerState()
        data object Idle : EqualizerState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}