package com.yes.player.presentation.contract

import com.yes.core.presentation.ui.UiEffect
import com.yes.core.presentation.ui.UiEvent
import com.yes.core.presentation.ui.UiState
import com.yes.player.presentation.model.PlayerStateUI

class PlayerContract {

    sealed class Event : UiEvent {
        data class OnSeek(val position:Int) : Event()
        data object OnPlay : Event()
        data object OnSeekToPrevious : Event()
        data object OnSeekToNext : Event()
    }

    data class State(
        val playerState: PlayerState,
        val info: PlayerStateUI?=null
    ) : UiState


    sealed class PlayerState {
        data object Success:PlayerState()
        data object Idle : PlayerState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}