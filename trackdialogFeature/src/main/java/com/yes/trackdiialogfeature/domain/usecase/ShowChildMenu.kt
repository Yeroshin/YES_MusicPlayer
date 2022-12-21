package com.yes.trackdiialogfeature.domain.usecase

import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.common.BaseResult
import com.yes.trackdiialogfeature.domain.entity.Menu

class ShowChildMenu(
    private val menuRepository: MenuRepository
) : UseCaseWithParam<MediaQuery, BaseResult<Menu>>() {
    override suspend fun run(params: MediaQuery): BaseResult<Menu> {
        return BaseResult.Success(menuRepository.getMenu(params))
    }
}