package com.yes.playlistdialogfeature.presentation.mapper

import com.yes.playlistdialogfeature.domain.entity.Item
import com.yes.playlistdialogfeature.presentation.model.ItemUi

class UiMapper {
    fun map(items: List<ItemUi>): List<Item> {
        var selected = false
        var currentIndex = 0
        return items.mapIndexed { index, item ->
            if (item.selected) {
                selected = true
            }
            if (item.current) {
                currentIndex = index
            }
            Item(
                item.id,
                item.name,
                item.selected
            )
        }.toMutableList().let {
            if (!selected) {
                it[currentIndex] = it[currentIndex].copy(current = true)
            }
            it
        }


    }

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