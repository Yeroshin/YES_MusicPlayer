package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class SetPresetValuesUseCase (
    dispatcher: CoroutineDispatcher,
    private val equalizerRepository: EqualizerRepositoryImpl
) : UseCase<Any?, Equalizer>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Equalizer> {
        val presets= mutableListOf<String>()
        return DomainResult.Success(
            Equalizer(
                enabled,
                presets,
                current
            )
        )
    }
}