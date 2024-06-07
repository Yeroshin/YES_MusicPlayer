package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.data.repository.SettingsRepositoryImpl

import kotlinx.coroutines.CoroutineDispatcher


class AddPlayListUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<AddPlayListUseCase.Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {
        params?.let {
            settingsRepository.updateCurrentPlaylistId(
                playListDialogRepositoryImpl.saveNewPlaylist(
                    it.name
                )
            )
        } ?:run {
            settingsRepository.updateCurrentPlaylistId(
                playListDialogRepositoryImpl.saveNewPlaylist(
                    ""
                )
            )
        }//TODO make check for unique playlist name
       /* val id = playListDialogRepositoryImpl.saveNewPlaylist(
            params?.name ?: ""
        )
        settingsRepository.updateCurrentPlaylistId(id)*/
        return DomainResult.Success(true)
    }

    data class Params(val name: String)


}