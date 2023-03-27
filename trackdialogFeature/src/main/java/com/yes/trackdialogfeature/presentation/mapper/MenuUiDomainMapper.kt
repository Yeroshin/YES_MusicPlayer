package com.yes.trackdialogfeature.presentation.mapper

import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

class MenuUiDomainMapper {
    fun map(
        menu: Menu,
        onClick:((TrackDialogContract.Event) -> Unit)
    ): MenuUi {
        /*val itemsUI = arrayListOf<MenuUi.MediaItem>()
        for (item in menu.children) {
            val itemUI = MenuUi.MediaItem(
                item.type,
                item.name,
                onClick
            )
            itemsUI.add(itemUI)
        }
        return MenuUi(menu.type, itemsUI)*/
        return MenuUi(
            menu.name,
            menu.children.map {
                MenuUi.MediaItem(
                    it.id,
                    it.name,
                    when(it.id){
                        1->1
                        else -> 1
                    } as Int,
                    onClick
                )
            }
        )

    }

}