package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.domain.model.MediaInfo
import com.yes.player.domain.model.Playlist
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SubscribeCurrentTrackInfoUseCase(
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,
) : UseCase<Any, Flow<MediaInfo>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<MediaInfo>> {
        return DomainResult.Success(
            playerRepository.subscribeCurrentTrackInfo()
        )
    }
}