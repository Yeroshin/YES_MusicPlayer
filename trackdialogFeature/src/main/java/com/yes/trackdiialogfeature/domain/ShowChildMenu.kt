package com.yes.trackdiialogfeature.domain

class ShowChildMenu(private val mediaRepository: IMenuRepository) : UseCaseWithParam<Menu, Menu>() {
    override suspend fun run(menu: Menu): Menu {
        val mediaItems = mediaRepository.getMedia(
            MediaQuery(
                menu.children[menu.selected].type,
                menu.type,
                menu.name
            )
        )
        /* val mediaItems=mediaRepository.getMedia(
             menu.children[menu.selected].type,
             menu.type,
             arrayListOf(menu.name))*/
        menu.children[menu.selected].items = mediaItems
        return menu.children[menu.selected]
    }
}