package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class SetPresetUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl
) : UseCase<SetPresetUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        return params?.let {
            val bands = mutableListOf<Int>()
            it.frequencies.forEach {frequencies->
                bands.add(equalizerRepository.getBand(frequencies))
            }
            if(params.preset>0){
                equalizerRepository.usePreset(
                    (params.preset-1)
                )
            }else{
               settingsRepository.getCustomPreset().forEachIndexed { index,value->
                   equalizerRepository.setBandLevel(bands[index],value)
               }
            }
            settingsRepository.setCurrentPreset(params.preset)
            val levels = mutableListOf<Int>()
            bands.forEach {band->
                levels.add(equalizerRepository.getBandLevel(band))
            }
            DomainResult.Success(
                Equalizer(
                    bandsLevelRange = equalizerRepository.getBandLevelRange(),
                    equalizerValues = levels,
                    currentPreset = params.preset
                )
            )
        }?: return DomainResult.Error(DomainResult.UnknownException)
    }
    data class Params(
        val preset:Int,
        val frequencies: IntArray
    )
}