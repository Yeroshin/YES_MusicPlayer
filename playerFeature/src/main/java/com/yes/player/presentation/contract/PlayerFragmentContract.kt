package com.yes.player.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState

class PlayerFragmentContract {

    sealed class Event : UiEvent {
        data class OnItemClicked(val id: Int? = null, val name: String = "") : Event()
        data object OnItemBackClicked : Event()
        data object OnButtonCancelClicked : Event()

    }

    data class State(
        val trackDialogState: PlayerFragmentState
    ) : UiState


    sealed class PlayerFragmentState {
        data object Idle : PlayerFragmentState()
        data object Dismiss : PlayerFragmentState()
        data object Loading : PlayerFragmentState()

    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}