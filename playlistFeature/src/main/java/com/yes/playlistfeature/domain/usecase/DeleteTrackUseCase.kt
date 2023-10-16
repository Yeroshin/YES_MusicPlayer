package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistfeature.domain.entity.Track
import kotlinx.coroutines.CoroutineDispatcher
import com.yes.playlistfeature.domain.usecase.DeleteTrackUseCase.Params
import kotlinx.coroutines.flow.last

class DeleteTrackUseCase (
    dispatcher: CoroutineDispatcher,
    private val playListRepositoryImpl: PlayListRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {

        return params?.let {
            playListRepositoryImpl.deleteTrack(it.track)

            playListRepositoryImpl.subscribeTracksWithPlaylistId(
                settingsRepository.subscribeCurrentPlaylistId().last()
            ).last().forEachIndexed { index, item ->
                item.copy(position = index)
                playListRepositoryImpl.updateTrack(item)
            }
            DomainResult.Success(true)
        }?:DomainResult.Error(DomainResult.UnknownException)

    }
    data class Params(
        val track:Track
    )
}