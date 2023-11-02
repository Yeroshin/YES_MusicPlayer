package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.data.repository.VisualizerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SubscribeVisualizerUseCase(
    dispatcher: CoroutineDispatcher,
    private val visualizerRepository: VisualizerRepository
) : UseCase<Unit?, Flow<ByteArray?>>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Flow<ByteArray?>> {
        return DomainResult.Success(
            visualizerRepository.subscribeVisualizer()
        )
    }
}

