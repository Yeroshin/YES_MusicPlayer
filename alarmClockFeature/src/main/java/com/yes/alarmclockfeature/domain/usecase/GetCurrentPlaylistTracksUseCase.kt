package com.yes.alarmclockfeature.domain.usecase


import com.yes.alarmclockfeature.data.repository.PlayListRepositoryImpl
import com.yes.alarmclockfeature.data.repository.SettingsRepositoryImpl
import com.yes.alarmclockfeature.domain.model.Track
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetCurrentPlaylistTracksUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListRepositoryImpl: PlayListRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, List<Track>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<List<Track>> {
        return DomainResult.Success(
            settingsRepository.subscribeCurrentPlaylistId().flatMapLatest {playlistId ->
                playListRepositoryImpl.subscribeTracksWithPlaylistId(playlistId )
                    .map { tracks ->
                        tracks.sortedBy { it.position }
                    }
            }.first()
        )
    }
}