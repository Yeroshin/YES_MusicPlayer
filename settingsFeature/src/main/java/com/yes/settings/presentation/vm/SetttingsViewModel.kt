package com.yes.settings.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.core.presentation.BaseViewModel
import com.yes.settings.presentation.contract.SettingsContract

class SettingsViewModel(

): BaseViewModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>() {
    override fun createInitialState(): SettingsContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: SettingsContract.Event) {
        TODO("Not yet implemented")
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