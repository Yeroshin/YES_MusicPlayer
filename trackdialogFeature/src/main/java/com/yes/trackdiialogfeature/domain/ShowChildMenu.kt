package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.entity.MenuParam

class ShowChildMenu(private val mediaRepository: IMenuRepository) : UseCase<Menu?, Menu>() {
    override suspend fun run(menu: Menu?): Menu {
        return mediaRepository.getMenu(MenuParam("arrayListOf()", "", arrayListOf(), "", null))
    }
}