package com.yes.trackdialogfeature.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.trackdialogfeature.presentation.model.MenuUi

class TrackDialogContract {
    sealed class Event : UiEvent {
        data class OnItemClicked(val id:Int?=null,val name :String="") : Event()
        data object OnItemBackClicked : Event()
        data object OnButtonCancelClicked : Event()
        data class OnButtonOkClicked(val items:List<MenuUi.ItemUi>) : Event()
    }

    data class State(
        val trackDialogState: TrackDialogState
    ) : UiState



    sealed class TrackDialogState {
        data object Idle : TrackDialogState()
        data object Dismiss:TrackDialogState()
        data object Loading : TrackDialogState()
        data class Success(val menu: MenuUi) : TrackDialogState() {
            val title: String = menu.title

        }
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}