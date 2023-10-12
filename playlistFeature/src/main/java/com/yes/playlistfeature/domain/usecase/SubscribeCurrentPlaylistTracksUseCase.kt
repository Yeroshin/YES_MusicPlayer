package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistfeature.domain.entity.Track
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class SubscribeCurrentPlaylistTracksUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListRepositoryImpl: PlayListRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, Flow<List<Track>>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<List<Track>>> {
        return DomainResult.Success(
            settingsRepository.subscribeCurrentPlaylistId().flatMapLatest {
                playListRepositoryImpl.subscribeTracksWithPlaylistId(it)
            }
        )
    }
}