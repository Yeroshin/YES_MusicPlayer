package com.yes.trackdiialogfeature.domain.usecase

import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.domain.entity.Menu

class GetRootMenu(private val menuRepository: MenuRepository): UseCase<Menu>() {
    override suspend fun run(): Menu {
        return menuRepository.getMenu()
    }
}