package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class SetPresetValuesUseCase (
    dispatcher: CoroutineDispatcher,
    private val equalizerRepository: EqualizerRepositoryImpl
) : UseCase<SetPresetValuesUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        params?.let {
            equalizerRepository.usePreset(
                (params.preset-1).toShort()
            )
        }
        val bands = mutableListOf<Short>()
        val levels = mutableListOf<Short>()

        params?.frequencies?.forEach {
            bands.add(equalizerRepository.getBand(it))
        }
        val bandsLevelRange = equalizerRepository.getBandLevelRange()
        bands.forEach {
            levels.add(equalizerRepository.getBandLevel(it))
        }


        return DomainResult.Success(
            Equalizer(
                bandsLevelRange = bandsLevelRange,
                equalizerValues = levels
            )
        )
    }
    data class Params(
        val preset:Short,
        val frequencies: IntArray
    )
}