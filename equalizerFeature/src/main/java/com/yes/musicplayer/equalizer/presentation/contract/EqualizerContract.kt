package com.yes.musicplayer.equalizer.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState

class EqualizerContract {

    sealed class Event : UiEvent {
        data class OnSeek(val position:Int) : Event()
        data object OnPlay : Event()
        data object OnSeekToPrevious : Event()
        data object OnSeekToNext : Event()
    }

    data class State(
        val equalizer: EqualizerState
    ) : UiState


    sealed class EqualizerState {
      //  data class Success(val info: EqualizerStateUI):EqualizerState()
        data object Idle : EqualizerState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}