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

) : UseCase<DeletePlayListUseCase.Params, Int>(dispatcher) {


    override suspend fun run(params: Params?): DomainResult<Int> {
        return params?.let {playlist->


                playListDialogRepositoryImpl.subscribePlaylists()
                    .first().let {
                        if (it.size>1){
                            return DomainResult.Success(
                                playListDialogRepositoryImpl.deletePlaylist(
                                    playlist.item
                                )
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