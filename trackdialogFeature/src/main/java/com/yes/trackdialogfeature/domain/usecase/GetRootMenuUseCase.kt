package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException
import com.yes.trackdialogfeature.domain.entity.Menu

class GetRootMenuUseCase(
    private val menuRepository: MenuRepository
) : UseCase<GetRootMenuUseCase.Params, Menu>() {
    override fun run(params: Params): Result<Menu> {
        return menuRepository.getMenu()
    }
    data class Params(val params: Unit)
}