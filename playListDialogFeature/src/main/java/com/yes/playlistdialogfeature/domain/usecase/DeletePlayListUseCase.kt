package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.domain.entity.Item
import kotlinx.coroutines.CoroutineDispatcher

class DeletePlayListUseCase(
    dispatcher: CoroutineDispatcher,

):UseCase<DeletePlayListUseCase.Params,Long>(dispatcher) {


    override suspend fun run(params: Params?): DomainResult<Long> {
        TODO("Not yet implemented")
    }
    data class Params(
        val item: Item
    )
}