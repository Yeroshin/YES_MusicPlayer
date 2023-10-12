package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistdialogfeature.domain.entity.Item
import com.yes.playlistdialogfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SubscribePlayListsUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, Flow<List<Item>>>(dispatcher) {

    override suspend fun run(params: Any?): DomainResult<Flow<List<Item>>> {
        return DomainResult.Success(
            combine(
                settingsRepository.subscribeCurrentPlaylistId(),
                playListDialogRepositoryImpl.subscribePlaylists()
            ) { id, playLists ->
                playLists.map { item ->
                    if (item.id == id) {
                        item.current = true
                    }
                    item
                }
            }
        )
    }
}