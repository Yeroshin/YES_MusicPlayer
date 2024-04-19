package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class SubscribeEqualizerEnabledUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl,
) : UseCase<Any, Flow<Equalizer>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<Equalizer>> {


        return DomainResult.Success(
            settingsRepository.subscribeEqualizerEnabled().map{enabled->
                val currentPreset = settingsRepository.getCurrentPreset()
                equalizerRepository.usePreset(
                    currentPreset
                )
                val presetsNames = mutableListOf<String>()
                presetsNames.add(
                    settingsRepository.getEqualizerCustomPresetsName()
                )
                presetsNames.addAll(
                    equalizerRepository.getPresets()
                )
                Equalizer(
                    equalizerEnabled = enabled,
                    currentPreset = currentPreset,
                    presetsNames = presetsNames,
                  /*  bandsLevelRange = bandsLevelRange,
                    equalizerValues = levels,
                    equalizerValuesInfo = levels,
                    loudnessEnhancerEnabled = loudnessEnhancerEnabled,
                    loudnessEnhancerValue = loudnessEnhancerValue*/

                )

            }

         )
    }
}