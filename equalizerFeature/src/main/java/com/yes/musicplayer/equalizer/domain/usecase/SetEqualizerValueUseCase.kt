package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class SetEqualizerValueUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl
) : UseCase<SetEqualizerValueUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {

        return params?.let {
            equalizerRepository.setBandLevel(params.band, params.value)

            val bands = mutableListOf<Int>()
            it.frequencies.forEach { frequencies ->
                bands.add(equalizerRepository.getBand(frequencies))
            }
            val levels = mutableListOf<Int>()
            bands.forEach { band ->
                levels.add(equalizerRepository.getBandLevel(band))
            }
            DomainResult.Success(
                Equalizer(
                    equalizerValuesInfo = levels
                )
            )
        } ?: return DomainResult.Error(DomainResult.UnknownException)


    }

    data class Params(
        val band: Int,
        val value: Int,
        val frequencies: IntArray
    )
}