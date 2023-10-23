package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.core.repository.entity.TrackEntity
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.domain.entity.Track
import com.yes.playlistfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import com.yes.playlistfeature.domain.usecase.DeleteTrackUseCase.Params
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last

class DeleteTrackUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListRepository: PlayListRepositoryImpl,
    private val settingsRepository: SettingsRepository
) : UseCase<Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {

        return params?.let {
            playListRepository.deleteTrack(params.track)
            playListRepository.subscribeTracksWithPlaylistId(
                settingsRepository.subscribeCurrentPlaylistId().first()
            )
                .firstOrNull()
                ?.sortedBy { it.position }
                ?.forEachIndexed { index, track ->
                    playListRepository.updateTrack(
                        track.copy(position = index)
                    )
                }
            DomainResult.Success(true)
        } ?: DomainResult.Error(DomainResult.UnknownException)

    }

    data class Params(
        val track: Track
    )
}