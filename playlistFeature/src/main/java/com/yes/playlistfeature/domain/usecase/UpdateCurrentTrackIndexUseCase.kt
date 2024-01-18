package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow

class UpdateCurrentTrackIndexUseCase (
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,
) : UseCase<Any, Flow<Int>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<Int>> {
        return DomainResult.Success(
            playerRepository.subscribeCurrentTrackIndex()
        )
    }
}