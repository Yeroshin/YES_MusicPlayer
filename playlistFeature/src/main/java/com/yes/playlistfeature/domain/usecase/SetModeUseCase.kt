package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.domain.entity.Mode
import com.yes.playlistfeature.domain.entity.Track
import kotlinx.coroutines.CoroutineDispatcher

class SetModeUseCase (
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,

    ) : UseCase<Unit, Mode>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Mode> {
        return DomainResult.Success(
                if(playerRepository.getRepeatMode()){
                    playerRepository.disableRepeatMode()
                    playerRepository.enableShuffleMode()
                    Mode.ShuffleMode
                }else if (playerRepository.getShuffleMode()){
                    playerRepository.disableShuffleMode()
                    Mode.SequentialMode
                }else{
                    playerRepository.enableRepeatMode()
                    Mode.RepeatMode
                }
            )
    }
}