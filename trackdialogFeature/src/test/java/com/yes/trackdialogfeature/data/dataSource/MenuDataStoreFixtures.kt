package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

object MenuDataStoreFixtures {
    private val data = listOf(
        MenuDataStoreEntity(
            0,
            "categories",
            null,
        ),
        MenuDataStoreEntity(
            1,
            "artists",
            null,
        ),
        MenuDataStoreEntity(
            2,
            "albums",
            null,
        ),
        MenuDataStoreEntity(
            3,
            "tracks",
            null,
        ),
        MenuDataStoreEntity(
            4,
            null,
            "artist",
        ),
        MenuDataStoreEntity(
            6,
            null,
            "track",
        ),
    )

    fun getCategoriesMenu(): MenuDataStoreEntity {
        val menu = data.find { it.id == 0 }
        return menu!!
    }

    fun getCategoryItems(): List<MenuDataStoreEntity> {
        return data.filter { it.id in listOf(1, 2, 3) }
    }

    fun getArtistsMenu(): MenuDataStoreEntity {
        return data.find { it.id == 1 }!!
    }

    fun getArtistMenu(): MenuDataStoreEntity {
        return data.find { it.id == 4 }!!
    }

    fun getTrackMenu(): MenuDataStoreEntity {
        return data.find { it.id == 6 }!!
    }
    fun getRootMenuId():Int{
        return 0
    }
    fun getArtistMenuId():Int{
        return 4
    }
}