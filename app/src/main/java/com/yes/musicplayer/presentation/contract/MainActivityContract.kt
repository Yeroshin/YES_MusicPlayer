package com.yes.musicplayer.presentation.contract

import com.yes.core.presentation.model.Theme
import com.yes.core.presentation.ui.UiEffect
import com.yes.core.presentation.ui.UiEvent
import com.yes.core.presentation.ui.UiState
import com.yes.player.presentation.contract.PlayerContract
import com.yes.player.presentation.model.PlayerStateUI

class MainActivityContract {
    sealed class Event : UiEvent {
      //  data object OnGetTheme : Event()
        data class OnGetTheme(val callback:() -> Unit) : Event()

    }

    data class State(
        val state: ActivityState,
        val theme: Theme?
    ) : UiState


    sealed class ActivityState {
        data object Success:ActivityState()
        data object Idle : ActivityState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}