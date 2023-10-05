package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.domain.entity.Item
import com.yes.playlistdialogfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SubscribePlayListsUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
    private val settings: SettingsRepository
):UseCase<Any,Flow<List<Item>>>(dispatcher)  {

    override fun run(params:Any?): DomainResult<Flow<List<Item>>> {
        val currentPlaylistId=settings.getCurrentPlayListName()
        val playLists=playListDialogRepositoryImpl.subscribePlaylists()
        val currentPlaylist=playLists.map {
            it.map {item->
                if (item.name==currentPlaylistId){
                    item.current=true
                }
            }
        }
        return DomainResult.Success(
            playListDialogRepositoryImpl.subscribePlaylists()
        )

    }
}