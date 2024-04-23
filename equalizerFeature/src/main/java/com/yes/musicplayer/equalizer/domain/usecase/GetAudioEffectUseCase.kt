package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.LoudnessEnhancerRepository
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first

class GetAudioEffectUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl,
    private val loudnessEnhancerRepository: LoudnessEnhancerRepository
) : UseCase<GetAudioEffectUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        val presetsNames = mutableListOf<String>()
        val bands = mutableListOf<Int>()
      /*  val equalizerEnabled=settingsRepository.subscribeEqualizerEnabled().first()
        equalizerRepository.setEnabled(
            equalizerEnabled
        )*/
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
       /* if(currentPreset >0){
            equalizerRepository.usePreset(
                (currentPreset -1)
            )
        }else{
            settingsRepository.getCustomPreset().forEachIndexed { index,value->
                equalizerRepository.setBandLevel(bands[index],value)
            }
        }*/
        val levels = mutableListOf<Int>()
        bands.forEach {
            levels.add(equalizerRepository.getBandLevel(it))
        }


        return DomainResult.Success(
            Equalizer(
                equalizerEnabled = settingsRepository.subscribeEqualizerEnabled().first(),
                currentPreset = settingsRepository.getCurrentPreset(),
                presetsNames=presetsNames,
                bandsLevelRange = bandsLevelRange,
                equalizerValues = levels,
                loudnessEnhancerEnabled = settingsRepository.getLoudnessEnhancerEnabled(),
                loudnessEnhancerValue = settingsRepository.getLoudnessEnhancerTargetGain()
            )
        )
    }

    data class Params(
        val frequencies: IntArray
    )
}