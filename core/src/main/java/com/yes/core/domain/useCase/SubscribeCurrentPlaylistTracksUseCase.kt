package com.yes.core.domain.useCase

import com.yes.core.data.repository.PlayListRepositoryImpl
import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.entity.Track
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SubscribeCurrentPlaylistTracksUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListRepositoryImpl: PlayListRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, Flow<List<Track>>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<List<Track>>> {
        return DomainResult.Success(
            settingsRepository.subscribeCurrentPlaylistId().flatMapLatest {playlistId ->
                playListRepositoryImpl.subscribeTracksWithPlaylistId(playlistId )
                    .map { tracks ->
                        tracks.sortedBy { it.position }
                    }
            }
        )
    }
}