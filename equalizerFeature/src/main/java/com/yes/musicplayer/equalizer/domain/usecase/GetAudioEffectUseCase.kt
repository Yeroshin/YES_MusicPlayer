package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.LoudnessEnhancerRepository
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class GetAudioEffectUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl,
    private val loudnessEnhancerRepository: LoudnessEnhancerRepository
) : UseCase<GetAudioEffectUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        val presetsNames = mutableListOf<String>()
        val bands = mutableListOf<Int>()
        params?.frequencies?.forEach {
            bands.add(equalizerRepository.getBand(it))
        }
        val bandsLevelRange = equalizerRepository.getBandLevelRange()

        presetsNames.add(
            settingsRepository.getEqualizerCustomPresetsName()
        )
        presetsNames.addAll(
            equalizerRepository.getPresets()
        )

        val currentPreset = settingsRepository.getCurrentPreset()
        equalizerRepository.usePreset(
            currentPreset
        )
        val levels = mutableListOf<Int>()
        bands.forEach {
            levels.add(equalizerRepository.getBandLevel(it))
        }
        val loudnessEnhancerEnabled = settingsRepository.getLoudnessEnhancerEnabled()
        loudnessEnhancerRepository.setEnabled(loudnessEnhancerEnabled)
        val loudnessEnhancerValue = settingsRepository.getLoudnessEnhancerTargetGain()
        loudnessEnhancerRepository.setTargetGain(loudnessEnhancerValue)

        return DomainResult.Success(
            Equalizer(
                settingsRepository.getEqualizerEnabled(),
                currentPreset,
                presetsNames,
                bandsLevelRange,
                equalizerValues = levels,
                equalizerValuesInfo = levels,
                loudnessEnhancerEnabled = loudnessEnhancerEnabled,
                loudnessEnhancerValue = loudnessEnhancerValue

            )
        )
    }

    data class Params(
        val frequencies: IntArray
    )
}