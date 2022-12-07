package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.MenuRepository

class ShowChildMenu(
    private val mediaRepository: IMediaRepository,
    private val menuRepository: MenuRepository
) : UseCaseWithParam<Menu, Menu>() {
    override suspend fun run(menu: Menu): Menu {
        val query=MediaQuery(
            menu.type,
            menu.parent?.type,
            menu.title
        )
        val childrenItems =mediaRepository.getMedia(query)
        for(item in childrenItems){
            val itemMenu=menuRepository.getMenuChild(menu.name)
            itemMenu.parent=menu
            itemMenu.title=item.title
            menu.children.add(itemMenu)
        }
        return menu
    }
}