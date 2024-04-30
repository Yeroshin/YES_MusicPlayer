package com.yes.settings.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.core.presentation.ui.BaseViewModel
import com.yes.settings.presentation.contract.SettingsContract

class SettingsViewModel(

): BaseViewModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>() {
    override fun createInitialState(): SettingsContract.State {
        return SettingsContract.State(
            SettingsContract.SettingsState.Idle
        )
    }

    override fun handleEvent(event: SettingsContract.Event) {
        when(event){
            is SettingsContract.Event.OnSetTheme -> {}
            is SettingsContract.Event.OnPlay  -> {}
        }
    }
    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(


                ) as T
        }
    }
}