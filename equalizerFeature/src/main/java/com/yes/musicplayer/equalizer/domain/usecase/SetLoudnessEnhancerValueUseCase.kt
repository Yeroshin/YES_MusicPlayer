package com.yes.musicplayer.equalizer.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.musicplayer.equalizer.data.repository.LoudnessEnhancerRepository
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import kotlinx.coroutines.CoroutineDispatcher

class SetLoudnessEnhancerValueUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl,
    private val loudnessEnhancerRepository: LoudnessEnhancerRepository
) : UseCase<SetLoudnessEnhancerValueUseCase.Params?, Equalizer>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Equalizer> {
        return params?.let {
            loudnessEnhancerRepository.setTargetGain(params.gainMB)
            settingsRepository.setLoudnessEnhancerTargetGain(params.gainMB)
            DomainResult.Success(
                Equalizer()
            )
        } ?: return DomainResult.Error(DomainResult.UnknownException)
    }

    data class Params(
        val gainMB:Int
    )
}