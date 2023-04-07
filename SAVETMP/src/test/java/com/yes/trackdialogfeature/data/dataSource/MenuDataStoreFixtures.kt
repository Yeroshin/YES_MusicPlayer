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


    fun getCategoriesChildren(): Array<MenuDataStoreEntity> {
        return arrayOf(
            MenuDataStoreEntity(
                1,
                "artists",
                null,
                0
            ),
            MenuDataStoreEntity(
                2,
                "albums",
                null,
                0
            ),
            MenuDataStoreEntity(
                3,
                "tracks",
                null,
                0
            )
        )
    }

    fun getAlbumsChildren(): Array<MenuDataStoreEntity> {
        return arrayOf(
            MenuDataStoreEntity(
                5,
                null,
                "album",
                2
            )
        )
    }
    fun getAlbumChildren(): Array<MenuDataStoreEntity> {
        return arrayOf(
            MenuDataStoreEntity(
                8,
                null,
                "track",
                5
            )
        )
    }

    fun getAlbums(): MenuDataStoreEntity {
        return MenuDataStoreEntity(
            2,
            "albums",
            null,
            0
        )
    }
    fun getAlbum(): MenuDataStoreEntity {
        return MenuDataStoreEntity(
            5,
            null,
            "album",
            2
        )
    }
    fun getExeption(){

    }
}