package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class GetPresetValuesUseCase(
    dispatcher: CoroutineDispatcher,
    private val equalizerRepository: EqualizerRepositoryImpl
) : UseCase<GetPresetValuesUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        val bands = mutableListOf<Short>()
        val levels = mutableListOf<Short>()
        params?.frequencies?.forEach {
            bands.add(equalizerRepository.getBand(it))
        }
        val bandsLevelRanges = mutableListOf<IntArray>()
        bands.forEach {
            bandsLevelRanges.add(equalizerRepository.getBandLevelRange(it))
            levels.add(equalizerRepository.getBandLevel(it))
        }
        params?.let {
            equalizerRepository.usePreset(params.preset)
        }

        return DomainResult.Success(
            Equalizer(
                bandsLevelRanges = bandsLevelRanges,
                equalizerValues = levels
            )
        )
    }

    data class Params(
        val preset: Short,
        val frequencies: IntArray
    )
}