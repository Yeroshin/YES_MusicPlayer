package com.yes.settings.presentation.contract

import com.yes.core.presentation.model.Theme
import com.yes.core.presentation.ui.UiEffect
import com.yes.core.presentation.ui.UiEvent
import com.yes.core.presentation.ui.UiState


class SettingsContract {
    sealed class Event : UiEvent {
        data class OnSetTheme(val heme: Theme) : Event()
        data object OnGetTheme : Event()
        data object OnIdle:Event()

    }

    data class State(
        val state: SettingsState,
        val theme: Theme?=null
        ) : UiState


    sealed class SettingsState {
        data object Success:SettingsState()
        data object Idle : SettingsState()
        data object Recreate:SettingsState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}