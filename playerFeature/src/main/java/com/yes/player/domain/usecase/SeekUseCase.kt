package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.domain.usecase.SeekUseCase.Params
import com.yes.player.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineDispatcher

class SeekUseCase (
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository
): UseCase<Params, Unit>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Unit> {
        params?.let {
            playerRepository.seek(it.position)
            return DomainResult.Success(Unit)
        } ?:return DomainResult.Error(DomainResult.UnknownException)
    }
    data class Params(val position:Long)
}