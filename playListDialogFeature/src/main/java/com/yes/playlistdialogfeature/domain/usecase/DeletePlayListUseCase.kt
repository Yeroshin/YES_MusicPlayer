package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistdialogfeature.domain.entity.Item
import com.yes.playlistdialogfeature.domain.entity.PlaylistException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last

class DeletePlayListUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<DeletePlayListUseCase.Params, Int>(dispatcher) {


    override suspend fun run(params: Params?): DomainResult<Int> {
        return params?.let {playlist->


                playListDialogRepositoryImpl.subscribePlaylists()
                    .first().let {playlists->
                        if (playlists.size>1){
                            var currentPlaylistId=settingsRepository.subscribeCurrentPlaylistId()
                                .first()
                            playListDialogRepositoryImpl.deletePlaylist(
                                playlist.item
                            )
                            if(playlist.item.id==currentPlaylistId){
                                settingsRepository.updateCurrentPlaylistId(
                                    playlists.filterNot {
                                        it.id == currentPlaylistId
                                    }.last().id
                                )
                            }
                            return DomainResult.Success(
                                currentPlaylistId.toInt()
                            )
                        }else{
                            return DomainResult.Error(PlaylistException.PlaylistsSizeLimit)
                        }
                    }


           /* DomainResult.Success(
                playListDialogRepositoryImpl.deletePlaylist(
                    it.item
                )
            )*/
        } ?: run {
            return DomainResult.Error(DomainResult.UnknownException)
        }
    }

    data class Params(
        val item: Item
    )
}