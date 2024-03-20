package com.yes.trackdialogfeature.presentation.mapper

import android.provider.MediaStore
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
                    mapStringToConst(it.type),
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
    private fun mapStringToConst(string: String?): Int {
        return when (string) {
            "artist" -> 1
            "album" -> 1
            "track" -> 2
            else -> 1
        }
    }
    fun map(item:MenuUi.ItemUi):Menu.Item{
        return Menu.Item(
            item.id,
            item.name,
            "",
            item.selected?:false
        )
    }

}