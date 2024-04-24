package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.core.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.SettingsRepository
import com.yes.core.domain.models.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.domain.useCase.UseCase
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first

class SaveTracksToPlaylistUseCase(
    dispatcher: CoroutineDispatcher,
    private val mediaRepositoryImpl: MediaRepositoryImpl,
    private val playListRepository: IPlayListDao,
    private val settingsRepository: SettingsRepository,
    private val menuRepository: IMenuRepository
) : UseCase<Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {
        val trackEntities = mutableListOf<PlayListDataBaseTrackEntity>()
        val playListId = settingsRepository.subscribeCurrentPlayListId().first()
        var lastPosition = playListRepository
            .subscribeTracksWithPlaylistId(playListId)
            .first()
            .maxByOrNull {
                it.position
            }?.position ?: -1
//TODO sort by artist name etc
        params?.items?.filter {
            it.selected
        }?.onEach { mediaItem ->

            if (isNetworkPath(mediaItem.name)) {
                lastPosition++
                trackEntities.add(
                    PlayListDataBaseTrackEntity(
                        playlistId = playListId,
                        title = mediaItem.name,
                        uri = mediaItem.name,
                        position = lastPosition
                    )
                )
            } else {
                trackEntities.addAll(
                    mediaRepositoryImpl.getAudioItems(
                        menuRepository.getItem(mediaItem.id)?.type,
                        mediaItem.name
                    ) .sortedWith(compareBy({ it.artist }, { it.title })).map {
                        lastPosition++
                        it.copy(
                            playlistId = playListId,
                            position = lastPosition
                        )
                    }
                )
            }

        }
        val saveResult = playListRepository.saveTracks(trackEntities)
        return if (saveResult.all { it > 0 }) {
            DomainResult.Success(true)
        } else {
            DomainResult.Error(DomainResult.UnknownException)
        }


    }

    private fun isNetworkPath(input: String): Boolean {
        val audioUrlPrefixes = listOf("http://", "https://", "ftp://", "rtsp://", "rtmp://")
        return audioUrlPrefixes.any { input.startsWith(it, ignoreCase = true) }
    }

    data class Params(val items: List<Item>)
}
