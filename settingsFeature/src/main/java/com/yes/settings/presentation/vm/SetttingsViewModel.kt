package com.yes.settings.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.model.Theme
import com.yes.core.presentation.ui.BaseViewModel
import com.yes.settings.domain.usecase.GetThemeUseCase
import com.yes.settings.domain.usecase.SetThemeUseCase
import com.yes.settings.presentation.contract.SettingsContract
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setThemeUseCase: SetThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : BaseViewModel<SettingsContract.Event, SettingsContract.State, SettingsContract.Effect>() {
   init {
       getTheme()
   }
    override fun createInitialState(): SettingsContract.State {
        return SettingsContract.State(
            SettingsContract.SettingsState.Idle
        )
    }

    override fun handleEvent(event: SettingsContract.Event) {
        when (event) {
            is SettingsContract.Event.OnSetTheme -> {
                setTheme(event.heme)
            }

            is SettingsContract.Event.OnGetTheme -> {
                getTheme()
            }

            SettingsContract.Event.OnIdle -> {
                idle()
            }
        }
    }
    private fun idle(){
        setState {
            copy(
                state=SettingsContract.SettingsState.Success,
            )
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            val result = getThemeUseCase()
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state=SettingsContract.SettingsState.Success,
                            theme=result.data
                        )
                    }
                }

                is DomainResult.Error -> {}

            }
        }
    }

    private fun setTheme(theme: Theme) {
        viewModelScope.launch {
            val result = setThemeUseCase(
                SetThemeUseCase.Params(theme = theme)
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state=SettingsContract.SettingsState.Recreate,
                            theme=theme
                        )
                    }
                }

                is DomainResult.Error -> {}

            }
        }
    }

    class Factory(
        private val setThemeUseCase: SetThemeUseCase,
        private val getThemeUseCase: GetThemeUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(
                setThemeUseCase,
                getThemeUseCase
            ) as T
        }
    }
}