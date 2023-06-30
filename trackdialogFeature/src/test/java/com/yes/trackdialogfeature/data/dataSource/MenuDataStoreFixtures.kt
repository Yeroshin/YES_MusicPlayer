package com.yes.trackdialogfeature.data.dataSource

import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

object MenuDataStoreFixtures {
    val data = listOf(
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


    fun getArtistsMenuDataStore(): Fixture<MenuDataStoreEntity> {
        val menu= data.find { it.id==1 }
        return Fixture(
            mapOf(
                "id" to 1
            ),
            menu!!
        )
    }
    fun getArtistMenuDataStore(): Fixture<MenuDataStoreEntity> {
        val menu= data.find { it.id==4 }
        return Fixture(
            mapOf(
                "id" to 4
            ),
            menu!!
        )
    }
    fun getArtistListMenuDataStore(): Fixture<List<MenuDataStoreEntity>> {
        val menu= data.filter { it.id==4 }
        return Fixture(
            mapOf(
                "id" to 1
            ),
            menu
        )
    }

    fun getTracksMenuDataStore(): Fixture<List<MenuDataStoreEntity>> {
        val menu= data.filter { it.id==6 }
        return Fixture(
            mapOf(
                "id" to 4
            ),
            menu
        )
    }
}