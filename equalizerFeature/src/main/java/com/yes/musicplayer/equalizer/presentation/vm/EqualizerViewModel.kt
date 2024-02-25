package com.yes.musicplayer.equalizer.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.domain.usecase.GetEqualizerUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerEnabledUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerValueUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetPresetUseCase
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract.*
import com.yes.musicplayer.equalizer.presentation.mapper.MapperUI
import kotlinx.coroutines.launch

class EqualizerViewModel(
    private val mapperUI: MapperUI,
    private val getEqualizerUseCase: GetEqualizerUseCase,
    private val setPresetUseCase: SetPresetUseCase,
    private val setEqualizerValueUseCase: SetEqualizerValueUseCase,
    private val setEqualizerEnabledUseCase: SetEqualizerEnabledUseCase
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
                            state = EqualizerState.Success(
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

            is Event.OnPresetSelected -> {
                setPreset(event.preset)
            }

            is Event.OnEqualizerValue ->{
               setEqualizerValue(event.band,event.value,event.maxLevelRange)
            }

            is Event.OnEqualizerEnabled -> setEqualizerEnabled(event.enabled)
        }
    }
    private fun setEqualizerEnabled(enabled:Boolean){
        viewModelScope.launch {
            val result = setEqualizerEnabledUseCase(
                SetEqualizerEnabledUseCase.Params(
                    enabled
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Success(
                                mapperUI.map(result.data)
                            )
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun setEqualizerValue(band:Int,value: Int,maxLevelRange: Int) {
        viewModelScope.launch {
            val result = setEqualizerValueUseCase(

                SetEqualizerValueUseCase.Params(
                    band,
                    mapperUI.mapUiEqualizerValueToDomain(
                        value,
                        maxLevelRange
                    )
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Success(
                                mapperUI.map(result.data)
                            )
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun setPreset(preset: Short) {
        viewModelScope.launch {
            val result = setPresetUseCase(
                SetPresetUseCase.Params(
                    preset,
                    intArrayOf(60000, 230000, 910000, 3000000, 14000000)
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Success(
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
        private val mapperUI: MapperUI,
        private val getEqualizerUseCase: GetEqualizerUseCase,
        private val setPresetUseCase: SetPresetUseCase,
        private val setEqualizerValueUseCase: SetEqualizerValueUseCase,
        private val setEqualizerEnabledUseCase: SetEqualizerEnabledUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EqualizerViewModel(
                mapperUI,
                getEqualizerUseCase,
                setPresetUseCase,
                setEqualizerValueUseCase,
                setEqualizerEnabledUseCase
            ) as T
        }
    }
}