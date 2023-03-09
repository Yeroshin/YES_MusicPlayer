package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

object MenuDataStoreFixtures {
    fun getRoot(): MenuDataStoreEntity {
        return MenuDataStoreEntity(
            0,
            "categories",
            null,
            null
        )
    }
    fun getParent():Int{
        return 0
    }
    fun getChildren(): Array<MenuDataStoreEntity> {
        return arrayOf(
            MenuDataStoreEntity(
                 1,
                "artists",
                "artist",
                0
            ),
            MenuDataStoreEntity(
                2,
                "albums",
                "album",
                0
            )
        )
    }
}