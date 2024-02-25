package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class SetEqualizerEnabledUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val equalizerRepository: EqualizerRepositoryImpl
) : UseCase<SetEqualizerEnabledUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        return params?.let {
            equalizerRepository.setEnabled(params.enabled)
            settingsRepository.
            DomainResult.Success(
                Equalizer(
                    equalizerEnabled = params.enabled
                )
            )
        } ?: return DomainResult.Error(DomainResult.UnknownException)
    }

    data class Params(
        val enabled: Boolean
    )
}