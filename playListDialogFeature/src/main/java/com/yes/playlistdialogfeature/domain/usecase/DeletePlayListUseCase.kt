package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.domain.entity.Item
import kotlinx.coroutines.CoroutineDispatcher

class DeletePlayListUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
) : UseCase<DeletePlayListUseCase.Params, Int>(dispatcher) {


    override suspend fun run(params: Params?): DomainResult<Int> {
        return params?.let {
            DomainResult.Success(
                playListDialogRepositoryImpl.deletePlaylist(
                    it.item
                )
            )
        } ?: run {
            return DomainResult.Error(DomainResult.UnknownException)
        }
    }

    data class Params(
        val item: Item
    )
}