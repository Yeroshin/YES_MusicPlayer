package com.yes.playlistdialogfeature.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.playlistdialogfeature.presentation.model.ItemUi

class PlayListDialogContract {
    sealed class Event : UiEvent {
        data class OnButtonAddClicked(val name:String):Event()
        data object OnButtonCancelClicked : Event()
        data class OnButtonOkClicked(val items:List<ItemUi>) : Event()
    }
    sealed class PlayListDialogState{
        data object Idle :PlayListDialogState()
        data object Dismiss:PlayListDialogState()
        data object Loading :PlayListDialogState()
        data class Success(val items: List<ItemUi>) : PlayListDialogState()
    }
    data class State(
        val playListDialogState: PlayListDialogState
    ) : UiState

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }

}