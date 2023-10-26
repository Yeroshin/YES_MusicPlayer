package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.domain.model.PlayerState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SubscribeCurrentTrackInfoUseCase(
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,
) : UseCase<Any, Flow<PlayerState>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<PlayerState>> {
        return DomainResult.Success(
            playerRepository.subscribeCurrentTrackInfo()
        )
    }
}