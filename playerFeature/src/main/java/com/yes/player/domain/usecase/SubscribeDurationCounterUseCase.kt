package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.domain.model.DurationCounter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class SubscribeDurationCounterUseCase(
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository
) : UseCase<Unit, Flow<DurationCounter>>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Flow<DurationCounter>> {
        return DomainResult.Success(
            playerRepository.subscribeCurrentPosition()
        )
    }
}