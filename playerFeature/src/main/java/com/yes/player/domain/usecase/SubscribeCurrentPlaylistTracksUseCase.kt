package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.domain.model.Track
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class SubscribeCurrentPlaylistTracksUseCase (
    dispatcher: CoroutineDispatcher,
    private val playListRepositoryImpl: PlaylistRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, Flow<List<Track>>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<List<Track>>> {
        return DomainResult.Success(
            settingsRepository.subscribeCurrentPlaylistId().flatMapLatest {playlistId ->
                playListRepositoryImpl.subscribeTracksWithPlaylistId(playlistId )
                /*  .map { tracks ->
                      tracks.sortedBy { it.position }
                  }*/
            }
        )
    }
}