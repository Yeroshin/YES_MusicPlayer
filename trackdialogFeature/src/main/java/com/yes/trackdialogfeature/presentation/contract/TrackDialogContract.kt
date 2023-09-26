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
            //val title: String = menu.title
           override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other == null || other !is Success) return false
                val onClick: (TrackDialogContract.Event) -> Unit = {}
                return menu.items.map { it.copy (onClick =onClick ) } == other.menu.items.map { it.copy (onClick=onClick) }
                        && menu.title == other.menu.title
            }
        }
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}