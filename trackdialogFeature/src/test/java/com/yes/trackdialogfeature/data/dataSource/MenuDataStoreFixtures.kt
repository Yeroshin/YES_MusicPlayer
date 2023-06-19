package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntityOld

object MenuDataStoreFixtures {
    fun getRoot(): MenuDataStoreEntityOld {
        return MenuDataStoreEntityOld(
            0,
            "categories",
            null,
            null
        )
    }


    fun getCategoriesChildren(): Array<MenuDataStoreEntityOld> {
        return arrayOf(
            MenuDataStoreEntityOld(
                1,
                "artists",
                null,
                0
            ),
            MenuDataStoreEntityOld(
                2,
                "albums",
                null,
                0
            ),
            MenuDataStoreEntityOld(
                3,
                "tracks",
                null,
                0
            )
        )
    }

    fun getAlbumsChildren(): Array<MenuDataStoreEntityOld> {
        return arrayOf(
            MenuDataStoreEntityOld(
                5,
                null,
                "album",
                2
            )
        )
    }

    fun getAlbumChildren(): Array<MenuDataStoreEntityOld> {
        return arrayOf(
            MenuDataStoreEntityOld(
                8,
                null,
                "track",
                5
            )
        )
    }

    fun getAlbumsOld(): MenuDataStoreEntityOld {
        return MenuDataStoreEntityOld(
            2,
            "albums",
            null,
            0
        )
    }

    fun getAlbums(): MenuDataStoreEntityOld {
        return MenuDataStoreEntityOld(
            2,
            "albums",
            null,
            2
        )
    }

    fun getAlbum(): MenuDataStoreEntityOld {
        return MenuDataStoreEntityOld(
            5,
            null,
            "album",
            2
        )
    }

    fun getExeption() {

    }

    fun getRootItems(): List<MenuDataStoreEntity> {
        return listOf(
            MenuDataStoreEntity(
                1,
                "artists",
                null
            ),
            MenuDataStoreEntity(
                2,
                "albums",
                null
            ),
            MenuDataStoreEntity(
                3,
                "tracks",
                null
            ),
        )
    }
    fun getItem():MenuDataStoreEntity{
        return MenuDataStoreEntity(
            1,
            "artists",
            null
        )
    }

}