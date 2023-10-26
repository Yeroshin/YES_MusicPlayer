package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineDispatcher

class PlayUseCase(
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository
): UseCase<Unit, Unit>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Unit> {
        if(playerRepository.isPlaying()){
            playerRepository.pause()
        }else{
            playerRepository.play()
        }
        return DomainResult.Success(Unit)
    }

}