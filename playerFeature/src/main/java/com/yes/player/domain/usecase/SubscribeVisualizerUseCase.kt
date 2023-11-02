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
    private val playerRepository: PlayerRepository,
    private val visualizerRepository: VisualizerRepository
) : UseCase<Any, Flow<ByteArray?>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<ByteArray?>> {
        return DomainResult.Success(
            playerRepository.subscribeAudioSessionId()
                .flatMapConcat {audioSessionId->
                visualizerRepository.subscribeVisualizer(audioSessionId)
            }
        )
    }
}