package com.yes.trackdialogfeature.presentation.mapper

import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

class UiMapper {
    fun map(
        menu: Menu,
        onClick: ((TrackDialogContract.Event) -> Unit)
    ): MenuUi {
        val map = mapOf(
            1 to 1
        )
        return MenuUi(
            menu.name,
            menu.children.map {
                MenuUi.ItemUi(
                    it.id,
                    it.name,
                    map.getOrElse(it.id) { 1 },
                    it.selected,
                    TrackDialogContract.Event.OnItemClicked(
                        it.id,
                        it.name,
                    ),
                    onClick
                )
            }
        )
    }
    fun map(item:MenuUi.ItemUi):Menu.Item{
        return Menu.Item(
            item.name,
            item.id,
            item.selected?:false
        )
    }

}