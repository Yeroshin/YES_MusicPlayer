package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SubscribePlayerCurrentTrackIndexUseCase (
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,
) : UseCase<Unit, Flow<Int>>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Flow<Int>> {
            return DomainResult.Success(
                playerRepository.subscribeCurrentTrackIndex()
            )
    }
}