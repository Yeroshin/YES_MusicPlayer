package com.yes.playlistdialogfeature.presentation.mapper

import com.yes.playlistdialogfeature.domain.entity.Item
import com.yes.playlistdialogfeature.presentation.model.ItemUi

class UiMapper {
    fun map(item: ItemUi): Item {
        return Item(
            item.id,
            item.name,
            item.selected
        )
    }
    fun map(item: Item): ItemUi {
        return ItemUi(
            item.id,
            item.name,
            item.current
        )
    }
}