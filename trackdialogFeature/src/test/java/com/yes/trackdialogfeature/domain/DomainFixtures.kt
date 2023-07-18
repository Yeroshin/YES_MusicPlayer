package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.entity.MenuException

object DomainFixtures {
    fun getError(): MenuException {
        return MenuException.Empty
    }

    fun getUnknownError(): DomainResult.DomainException {
        return DomainResult.UnknownException
    }

    private const val count = 5
    private val artists =
        MediaDataStoreFixtures.getArtistsListMedia().map {
            Item(
                4,
                it.title,
                "artist",
                false
            )
        }
    private val tracks =
        MediaDataStoreFixtures.getTracksListMedia().map {
            Item(
                7,
                it.title,
                "track",
                false
            )
        }
    private val categoryItems =listOf(
        Item(
            1,
            "artists",
            null,
            false
        ),
        Item(
            2,
            "albums",
            null,
            false
        ),
        Item(
            3,
            "tracks",
            null,
            false
        )
    )
    private val dataResult = listOf(
        Menu(
            "categories"
        ),
        Menu(
            "artists"
        ),
        Menu(
            artists[0].name
        ),

        )

    fun getCategoriesMenu(): Menu {
        val menu = dataResult.find { it.name == "categories" }?.copy()
        menu!!.children.toMutableList().addAll(categoryItems)
        return menu

    }
    fun getCategoryItems():List<Item>{
        return categoryItems
    }
    fun getArtistsItem():Item{
        return categoryItems[0]
    }
    fun getArtistItem():Item{
        val menuRepositoryMapper=MenuRepositoryMapper()
        return menuRepositoryMapper.mapToItem(
            MenuDataStoreFixtures.getArtistMenu()
        )
    }


    fun getPrimaryArtistsMenu(): Menu {
        return dataResult.find { it.name == "artists" }!!.copy()
    }
    fun getArtistsMenuItemsList(): List<Item> {
        return artists
    }
    fun getSecondArtistMenuItem():Item{
        return artists[1]
    }

    fun getArtistsMenu(): Menu {
        val menu = dataResult.find { it.name == "artists" }!!.copy()
        menu.children.toMutableList().addAll(artists)
        return menu
    }


    fun getPrimaryTracksMenu(): Menu {
        return dataResult.find { it.name == artists[0].name }!!
    }
    fun getTracksMenuItemsList(): List<Item> {
        return tracks
    }
    fun getTracksMenu(): Menu {
        val menu = dataResult.find { it.name == artists[0].name }!!
        menu.children.toMutableList().addAll(tracks)
        return menu
    }


}