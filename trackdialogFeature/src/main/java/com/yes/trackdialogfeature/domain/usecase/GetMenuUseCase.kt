package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class GetMenuUseCase(
    dispatcher: CoroutineDispatcher
) : UseCase<Params,Menu>(dispatcher) {
    override fun run(params: Params): DomainResult<Menu> {
        TODO("Not yet implemented")
    }
    data class Params(
        val id: Int,
        val name: String
    )
}