package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import kotlinx.coroutines.CoroutineDispatcher

class GetRootMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepository
) : UseCase<GetRootMenuUseCase.Params, Menu>(dispatcher) {
    override fun run(params: Params): DomainResult<Menu> {
        return menuRepository.getMenu()
    }
    data class Params(val params: Unit)
}