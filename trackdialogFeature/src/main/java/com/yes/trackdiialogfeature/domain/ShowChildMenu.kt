package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.entity.MenuParam

class ShowChildMenu(private val mediaRepository: IMenuRepository) : UseCase<Menu?, Menu>() {
    override suspend fun run(menu: Menu?): Menu {

        return mediaRepository.getMenu(
            MenuParam(
                menu!!.items[menu.selected].type,
                menu!!.parent!!.items[menu.selected].type,
                arrayListOf(menu!!.items[menu.selected].title),
                menu!!.items[menu.selected].title, menu
            )
        )
    }
}