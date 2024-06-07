package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SubscribeCurrentTrackIndexUseCase (
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,
) : UseCase<Any, Flow<Int>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<Int>> {
        return DomainResult.Success(
            playerRepository.subscribeCurrentTrackIndex()
        )
    }
}