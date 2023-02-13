package com.yes.trackdialogfeature.presentation.mapper

import com.yes.core.presentation.UiEvent
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

class MenuMapper {
    fun map(
        menu: Menu,
        onClick:((TrackDialogContract.Event) -> Unit)
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