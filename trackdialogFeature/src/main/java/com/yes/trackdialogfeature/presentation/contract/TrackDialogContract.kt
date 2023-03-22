package com.yes.trackdialogfeature.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.trackdialogfeature.presentation.model.MenuUi

class TrackDialogContract {
    sealed class Event : UiEvent {

        object OnGetRootMenu : Event()
        data class OnItemClicked(val id:Int,val name :String) : Event()
    }

    data class State(
        val trackDialogState: TrackDialogState
    ) : UiState



    sealed class TrackDialogState {
        object Loading : TrackDialogState()
        data class Success(val menu: MenuUi) : TrackDialogState() {
            val title: String = menu.title

        }
    }

    sealed class Effect : UiEffect {
        object UnknownException : Effect()
    }
}