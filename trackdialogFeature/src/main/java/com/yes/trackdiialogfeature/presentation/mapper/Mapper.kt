package com.yes.trackdiialogfeature.presentation.mapper

import com.yes.trackdiialogfeature.domain.entity.Menu
import com.yes.trackdiialogfeature.presentation.entity.MenuUi

class Mapper {
    fun map(menu:Menu): MenuUi {
        val itemsUI= arrayListOf<MenuUi.MediaItem>()
        for (item in menu.children){
            val itemUI=MenuUi.MediaItem(item.title)
            itemsUI.add(itemUI)
        }
        return  MenuUi(menu.title,menu.type,itemsUI)
    }

}