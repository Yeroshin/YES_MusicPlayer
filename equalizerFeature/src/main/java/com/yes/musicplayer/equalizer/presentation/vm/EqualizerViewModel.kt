package com.yes.musicplayer.equalizer.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.domain.usecase.GetEqualizerUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetPresetValuesUseCase
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract.*
import com.yes.musicplayer.equalizer.presentation.mapper.MapperUI
import kotlinx.coroutines.launch

class EqualizerViewModel(
    private val getEqualizerUseCase: GetEqualizerUseCase,
    private val mapperUI: MapperUI,
    private val setPresetValuesUseCase: SetPresetValuesUseCase
) : BaseViewModel<Event, State, Effect>() {

    init {
        viewModelScope.launch {
            val result = getEqualizerUseCase(
                GetEqualizerUseCase.Params(
                    intArrayOf(60000, 230000, 910000, 3000000, 14000000)
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Init(
                                mapperUI.map(result.data)
                            )
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }


    override fun createInitialState(): State {

        return State(
            EqualizerState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnPlay -> {

            }

            is Event.OnSeek -> {

            }

            is Event.OnPresetSelected -> {
                setPreset(event.preset)
            }
        }
    }

    private fun setPreset(preset: Short) {
        viewModelScope.launch {
            val result = setPresetValuesUseCase(
                SetPresetValuesUseCase.Params(
                    preset,
                    intArrayOf(60000, 230000, 910000, 3000000, 14000000)
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Init(
                                mapperUI.map(result.data)
                            )
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    class Factory(
        private val getEqualizerUseCase: GetEqualizerUseCase,
        private val mapperUI: MapperUI,
        private val setPresetValuesUseCase: SetPresetValuesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EqualizerViewModel(
                getEqualizerUseCase,
                mapperUI,
                setPresetValuesUseCase
            ) as T
        }
    }
}