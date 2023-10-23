package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.domain.entity.Track
import com.yes.playlistfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class SetTracksToPlayerPlaylistUseCase(
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,

    ) : UseCase<Params, Unit>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Unit> {
        return params?.let {
            playerRepository.setTracks(params.tracks)
            DomainResult.Success(Unit)
        }?:DomainResult.Error(DomainResult.UnknownException)

    }
    data class Params(
        val tracks: List<Track>
    )
}