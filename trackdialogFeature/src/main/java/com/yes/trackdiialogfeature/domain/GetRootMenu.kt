package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.MenuRepository

class GetRootMenu(private val menuRepository: MenuRepository):UseCase<Menu>() {
    override suspend fun run(): Menu {
        return menuRepository.getMenu()
    }
}