package com.yes.trackdiialogfeature.domain.usecase

import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.domain.common.BaseResult
import com.yes.trackdiialogfeature.domain.entity.Menu

class GetRootMenu(private val menuRepository: MenuRepository): UseCase<BaseResult<Menu>>() {
    override suspend fun run(): BaseResult<Menu> {
        return BaseResult.Success(menuRepository.getMenu())
    }
}