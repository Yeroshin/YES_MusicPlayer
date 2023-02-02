package com.yes.trackdialogfeature.presentation.contract

import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.trackdialogfeature.presentation.model.MenuUi

class TrackDialogContract {
    sealed class Event : UiEvent {
        data class OnItemClicked(val title:String,val name :String) : Event()
    }

    data class State(
        val trackDialogState: TrackDialogState
    ) : UiState

    sealed interface TrackDialogState {
        object Loading : TrackDialogState
        data class Success(val menu: MenuUi) : TrackDialogState {
            val title: String = menu.title

        }
    }
}