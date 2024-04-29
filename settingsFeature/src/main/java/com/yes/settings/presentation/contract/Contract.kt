package com.yes.settings.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState

class SettingsContract {
    sealed class Event : UiEvent {
        data class OnSeek(val position:Int) : Event()
        data object OnPlay : Event()

    }

    data class State(
        val settingsState: SettingsState,

        ) : UiState


    sealed class SettingsState {
        data object Success:SettingsState()
        data object Idle : SettingsState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}