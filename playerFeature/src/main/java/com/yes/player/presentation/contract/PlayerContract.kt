package com.yes.player.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.player.presentation.model.InfoUI

class PlayerContract {

    sealed class Event : UiEvent {
        data class OnSeek(val position:Int) : Event()
        data object OnPlay : Event()
        data object OnSeekToPrevious : Event()
        data object OnSeekToNext : Event()

    }

    data class State(
        val playerState: PlayerState
    ) : UiState


    sealed class PlayerState {
        data class Success(val info: InfoUI):PlayerState()
        data object Idle : PlayerState()
        data object Loading : PlayerState()

    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}