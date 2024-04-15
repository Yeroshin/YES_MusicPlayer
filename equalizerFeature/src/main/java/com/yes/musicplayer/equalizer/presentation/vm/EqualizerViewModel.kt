package com.yes.musicplayer.equalizer.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import com.yes.musicplayer.equalizer.domain.usecase.GetAudioEffectUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerEnabledUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerValueUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetLoudnessEnhancerEnabledUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetLoudnessEnhancerValueUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetPresetUseCase
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract.*
import com.yes.musicplayer.equalizer.presentation.mapper.MapperUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.max

class EqualizerViewModel(
    private val mapperUI: MapperUI,
    private val getAudioEffectUseCase: GetAudioEffectUseCase,
    private val setPresetUseCase: SetPresetUseCase,
    private val setEqualizerValueUseCase: SetEqualizerValueUseCase,
    private val setEqualizerEnabledUseCase: SetEqualizerEnabledUseCase,
    private val setLoudnessEnhancerEnabledUseCase: SetLoudnessEnhancerEnabledUseCase,
    private val setLoudnessEnhancerValueUseCase: SetLoudnessEnhancerValueUseCase
) : BaseViewModel<Event, State, Effect>() {
    private val frequencies = intArrayOf(60000, 230000, 910000, 3000000, 14000000)

    init {
        viewModelScope.launch {
            val result = getAudioEffectUseCase(
                GetAudioEffectUseCase.Params(
                    frequencies
                )
            )
            when (result) {
                is DomainResult.Success -> {

                    setState {
                        copy(
                            state = EqualizerState.Success,
                            equalizerEnabled = mapperUI.map(result.data).equalizerEnabled,
                            currentPreset = mapperUI.map(result.data).currentPreset,
                            presetsNames = mapperUI.map(result.data).presetsNames,
                            bandsLevelRange = mapperUI.map(result.data).bandsLevelRange,
                            equalizerValues = mapperUI.map(result.data).equalizerValues,
                            equalizerValuesInfo = mapperUI.map(result.data).equalizerValuesInfo,
                            loudnessEnhancerEnabled = mapperUI.map(result.data).loudnessEnhancerEnabled,
                            loudnessEnhancerValue = mapperUI.map(result.data).loudnessEnhancerValue,
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

            is Event.OnEqualizerValue -> setEqualizerValue(
                    event.band, event.value, event.maxLevelRange, event.seekBarValues
                )


            is Event.OnEqualizerEnabled -> setEqualizerEnabled(event.enabled)
            is Event.OnLoudnessEnhancerEnabled -> setLoudnessEnhancerEnabled(event.enabled)
            is Event.OnLoudnessEnhancerTargetGain -> setLoudnessEnhancerTargetGain(event.percent)
            is Event.OnEqualizerValueSet -> setEqualizerValueState(
                event.band, event.value, event.maxLevelRange, event.seekBarValues
            )

            is Event.OnLoudnessEnhancerTargetGainSet -> setLoudnessEnhancerState(event.percent)
        }
    }

    private fun setLoudnessEnhancerState(percent: Int) {
        setState {
            copy(
                state = EqualizerState.Success,
                loudnessEnhancerValue = percent
            )
        }
    }

    private fun setEqualizerValueState(
        band: Int,
        value: Int,
        maxLevelRange: Int,
        seekBarValues: IntArray
    ) {
        val jobs=setEqualizerValue(
            band,
            value,
            maxLevelRange,
            seekBarValues
        )
        viewModelScope.launch {
            jobs.joinAll()
            setState {
                println("setEqualizerValueState")
                copy(
                    state = EqualizerState.Success,
                    bandsLevelRange = maxLevelRange,
                    equalizerValues = seekBarValues
                )
            }
        }


    }
    private fun setEqualizerValue(
        band: Int,
        value: Int,
        maxLevelRange: Int,
        seekBarValues: IntArray
    ):ArrayList<Job> {
        val jobs = ArrayList<Job>()
        viewModelScope.launch {
            val result = setEqualizerValueUseCase(

                SetEqualizerValueUseCase.Params(
                    band,
                    mapperUI.mapUiEqualizerValueToDomain(
                        value,
                        maxLevelRange,
                    ),
                    frequencies,
                    seekBarValues.map {
                        mapperUI.mapUiEqualizerValueToDomain(
                            it,
                            maxLevelRange,
                        )
                    }.toIntArray()
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    println("setEqualizerValue")
                    setState {
                        copy(
                            state = EqualizerState.Success,
                            equalizerValuesInfo = mapperUI.map(result.data).equalizerValuesInfo,
                            equalizerValues = null,
                            bandsLevelRange = null,
                            currentPreset = mapperUI.map(result.data).currentPreset
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }.also { jobs.add(it) }
        return jobs
    }
    private fun setLoudnessEnhancerEnabled(enabled: Boolean) {
        viewModelScope.launch {
            val result = setLoudnessEnhancerEnabledUseCase(
                SetLoudnessEnhancerEnabledUseCase.Params(
                    enabled
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Success,
                            loudnessEnhancerEnabled = mapperUI.map(result.data).loudnessEnhancerEnabled
                        )
                        /*  copy(
                              state = EqualizerState.Success(
                                  mapperUI.map(result.data)
                              )
                          )*/
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun setLoudnessEnhancerTargetGain(percent: Int) {
        viewModelScope.launch {
            val result = setLoudnessEnhancerValueUseCase(
                SetLoudnessEnhancerValueUseCase.Params(
                    mapperUI.mapLoudnessEnhancerTargetGainPercentToValue(
                        percent
                    )
                )
            )
            when (result) {
                is DomainResult.Success -> {}

                is DomainResult.Error -> {}
            }
        }
    }

    private fun setEqualizerEnabled(enabled: Boolean) {
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
                            state = EqualizerState.Success,
                            equalizerEnabled = mapperUI.map(result.data).equalizerEnabled
                        )
                        /* copy(
                             state = EqualizerState.Success(
                                 mapperUI.map(result.data)
                             )
                         )*/
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }



    private fun setPreset(preset: Int) {
        viewModelScope.launch {
            val result = setPresetUseCase(
                SetPresetUseCase.Params(
                    preset.toShort(),
                    intArrayOf(60000, 230000, 910000, 3000000, 14000000)
                )
            )
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state = EqualizerState.Success,
                            currentPreset = preset,
                            equalizerValuesInfo = mapperUI.map(result.data).equalizerValuesInfo,
                            equalizerValues = mapperUI.map(result.data).equalizerValues
                        )
                        /* copy(
                             state = EqualizerState.Success(
                                 mapperUI.map(result.data)
                             )
                         )*/
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    class Factory(
        private val mapperUI: MapperUI,
        private val getAudioEffectUseCase: GetAudioEffectUseCase,
        private val setPresetUseCase: SetPresetUseCase,
        private val setEqualizerValueUseCase: SetEqualizerValueUseCase,
        private val setEqualizerEnabledUseCase: SetEqualizerEnabledUseCase,
        private val setLoudnessEnhancerEnabledUseCase: SetLoudnessEnhancerEnabledUseCase,
        private val setLoudnessEnhancerValueUseCase: SetLoudnessEnhancerValueUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EqualizerViewModel(
                mapperUI,
                getAudioEffectUseCase,
                setPresetUseCase,
                setEqualizerValueUseCase,
                setEqualizerEnabledUseCase,
                setLoudnessEnhancerEnabledUseCase,
                setLoudnessEnhancerValueUseCase
            ) as T
        }
    }
}