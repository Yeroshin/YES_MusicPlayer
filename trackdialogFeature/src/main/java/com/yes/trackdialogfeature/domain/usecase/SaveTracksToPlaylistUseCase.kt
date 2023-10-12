package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.domain.repository.ISettingsRepository
import com.yes.core.domain.models.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.repository.entity.TrackEntity
import com.yes.core.domain.useCase.UseCase
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class SaveTracksToPlaylistUseCase(
    dispatcher: CoroutineDispatcher,
    private val mediaRepositoryImpl: MediaRepositoryImpl,
    private val playListRepository: IPlayListDao,
    private val settingsRepository: ISettingsRepository,
    private val menuRepository: IMenuRepository
) : UseCase<Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {
        val selectedItems = params?.items?.filter {
            it.selected
        }
        val playListName = settingsRepository.getCurrentPlayListName()
        val trackEntities = mutableListOf<TrackEntity>()
        selectedItems?.onEach { mediaItem ->
            if (isNetworkPath(mediaItem.name)) {
                trackEntities.add(
                    TrackEntity(
                        playlistName = playListName,
                        title = mediaItem.name,
                        uri = mediaItem.name,
                    )
                )
            } else {
                trackEntities.addAll(
                    mediaRepositoryImpl.getAudioItems(
                        menuRepository.getItem(mediaItem.id)?.type,
                        mediaItem.name
                    ).map {
                        it.copy(playlistName = playListName)
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
