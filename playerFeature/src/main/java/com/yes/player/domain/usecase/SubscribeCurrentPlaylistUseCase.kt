package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.domain.model.Playlist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SubscribeCurrentPlaylistUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val playListRepositoryImpl: PlaylistRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, Flow<Playlist>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<Playlist>> {
        return DomainResult.Success(
            settingsRepository.subscribeCurrentPlaylistId()
                .map { playlistId ->
                    playListRepositoryImpl.getPlaylist(playlistId)
                }
                .flowOn(dispatcher)
        )
    }
}