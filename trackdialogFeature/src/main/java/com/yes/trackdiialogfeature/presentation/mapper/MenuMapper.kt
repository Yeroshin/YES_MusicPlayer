package com.yes.trackdiialogfeature.presentation.mapper

import com.yes.trackdiialogfeature.domain.entity.Menu
import com.yes.trackdiialogfeature.presentation.entity.MenuUi

class MenuMapper {
    fun map(
        menu: Menu,
        onClick:((String,String) -> Unit)
    ): MenuUi {
        val itemsUI = arrayListOf<MenuUi.MediaItem>()
        for (item in menu.children) {
            val itemUI = MenuUi.MediaItem(
                item.title,
                item.name,
                onClick
            )
            itemsUI.add(itemUI)
        }
        return MenuUi(menu.title, itemsUI)
    }

}