package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.SynchroUseCase
import com.yes.player.data.repository.PlayerRepository

class PlayUseCase(
    private val playerRepository: PlayerRepository
): SynchroUseCase<Unit, Unit>() {
    override fun run(params: Unit?): DomainResult<Unit> {
        playerRepository.play()
        return DomainResult.Success(Unit)
    }

}