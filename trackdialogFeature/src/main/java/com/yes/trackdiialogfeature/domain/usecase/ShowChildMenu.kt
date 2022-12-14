package com.yes.trackdiialogfeature.domain.usecase

import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.common.BaseResult

class ShowChildMenu(
    private val menuRepository: MenuRepository
) : UseCaseWithParam<MediaQuery, BaseResult<Any?>>() {
    override suspend fun run(query: MediaQuery): BaseResult<Any?> {
        return menuRepository.getMenu(query)
    }
}