package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.data.repository.SettingsRepositoryImpl

import kotlinx.coroutines.CoroutineDispatcher


class AddPlayListUseCase (
    dispatcher: CoroutineDispatcher,
    private val playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<AddPlayListUseCase.Params, Long>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Long> {
        val  id=playListDialogRepositoryImpl.saveNewPlaylist(
            params?.name?:""
        )
        settingsRepository.updateCurrentPlaylistId(id)
        return DomainResult.Success(
            id
        )
    }
    data class Params(val name: String)


}