package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.PlayerRepository
import com.yes.alarmclockfeature.domain.model.Track
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class SetTracksToPlayerPlaylistUseCase (
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,

    ) : UseCase<SetTracksToPlayerPlaylistUseCase.Params, Unit>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Unit> {
        return params?.let {
            playerRepository.setTracks(params.tracks)
            DomainResult.Success(Unit)
        }?: DomainResult.Error(DomainResult.UnknownException)

    }
    data class Params(
        val tracks: List<Track>
    )
}