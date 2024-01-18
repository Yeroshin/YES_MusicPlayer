package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SetSettingsTrackIndexUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepositoryImpl: SettingsRepositoryImpl,
) : UseCase<SetSettingsTrackIndexUseCase.Params, Int>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Int> {
        return params?.let {
            settingsRepositoryImpl.setCurrentTrackIndex(params.index)
            DomainResult.Success(1)
        }?:DomainResult.Error(DomainResult.UnknownException)

    }
    data class Params(
        val index:Int
    )
}