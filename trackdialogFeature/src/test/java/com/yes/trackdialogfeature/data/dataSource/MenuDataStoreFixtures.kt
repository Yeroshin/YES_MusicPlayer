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

    fun getParentParam(): Int {
        return 0
    }

    fun getCategoriesChildren(): Array<MenuDataStoreEntity> {
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

    fun getArtistsParam(): Int {
        return 1
    }

    fun getArtistsChildren(): Array<MenuDataStoreEntity> {
        return arrayOf(
            MenuDataStoreEntity(
                3,
                null,
                "artist",
                1
            ),

            )
    }

    fun getArtistChildren(): Array<MenuDataStoreEntity> {
        return arrayOf(
            MenuDataStoreEntity(
                4,
                "artistAlbums",
                "album",
                3
            ),
            MenuDataStoreEntity(
                5,
                "artistTracks",
                "track",
                3
            )

        )
    }
}