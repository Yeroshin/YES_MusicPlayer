package com.yes.core.domain.useCase

import com.yes.core.data.repository.EqualizerRepositoryImpl
import com.yes.core.data.repository.LoudnessEnhancerRepository
import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first

class InitEqualizerUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl,
    private val loudnessEnhancerRepository: LoudnessEnhancerRepository
    ) : UseCase<InitEqualizerUseCase.Params, Boolean>(dispatcher) {


    override suspend fun run(params: Params?): DomainResult<Boolean> {
        return params?.let {
            val bands = mutableListOf<Int>()
            it.frequencies.forEach {frequencies->
                bands.add(equalizerRepository.getBand(frequencies))
            }
            val equalizerEnabled=settingsRepository.subscribeEqualizerEnabled().first()
            equalizerRepository.setEnabled(
                equalizerEnabled
            )


            val currentPreset = settingsRepository.getCurrentPreset()
            if(currentPreset >0){
                equalizerRepository.usePreset(
                    (currentPreset -1)
                )
            }else{
                settingsRepository.getCustomPreset().forEachIndexed { index,value->
                    equalizerRepository.setBandLevel(bands[index],value)
                }
            }
            val loudnessEnhancerEnabled = settingsRepository.getLoudnessEnhancerEnabled()
            loudnessEnhancerRepository.setEnabled(loudnessEnhancerEnabled)
            val loudnessEnhancerValue = settingsRepository.getLoudnessEnhancerTargetGain()
            loudnessEnhancerRepository.setTargetGain(loudnessEnhancerValue)


            DomainResult.Success(
                true
            )
        }?: return DomainResult.Error(DomainResult.UnknownException)


    }
    data class Params(
        val frequencies: IntArray,
    )
}