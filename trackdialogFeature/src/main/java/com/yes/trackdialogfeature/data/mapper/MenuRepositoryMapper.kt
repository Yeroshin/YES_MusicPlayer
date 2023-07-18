package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

class MenuRepositoryMapper {

    fun mapToMenu(menuDataStoreEntity: MenuDataStoreEntity): Menu? {
        return Menu(
            menuDataStoreEntity.name
                ?: run {
                    return null
                }
        )
    }

    fun mapToItem(menuDataStoreEntity: MenuDataStoreEntity): Item {
        return Item(
            menuDataStoreEntity.id,
            menuDataStoreEntity.name ?: "",
            menuDataStoreEntity.type,
            false
        )
    }


}