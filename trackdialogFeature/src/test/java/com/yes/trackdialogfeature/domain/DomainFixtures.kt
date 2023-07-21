package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.entity.MenuException
import kotlinx.coroutines.NonCancellable.children

object DomainFixtures {
    fun getEmptyError(): MenuException {
        return MenuException.Empty
    }

    fun getUnknownError(): DomainResult.DomainException {
        return DomainResult.UnknownException
    }

    private const val count = 5
    private const val selectedItem=1
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
    private val categoryItems = listOf(
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
            "categories",
            listOf()
        ),
        Menu(
            "artists",
            listOf()
        ),
        Menu(
            artists[selectedItem].name,
            listOf()
        ),
        )
    fun getPrimaryCategoriesMenu(): Menu {
        val menu = dataResult.find { it.name == "categories" }
        return menu!!

    }
    fun getCategoriesMenu(): Menu {
        val menu = dataResult.find { it.name == "categories" }?.copy()
        menu!!.children.toMutableList().addAll(categoryItems)
        return menu

    }

    fun getCategoryItems(): List<Item> {
        return categoryItems
    }

    fun getArtistsItem(): Item {
        return categoryItems[0]
    }

    private val menuRepositoryMapper = MenuRepositoryMapper()
    fun getPrimaryArtistItem(): Item {
        return menuRepositoryMapper.mapToItem(
            MenuDataStoreFixtures.getArtistMenu()
        )
    }
    fun getPrimaryArtistsMenu(): Menu {
        return dataResult.find { it.name == "artists" }!!
    }
    fun getPrimaryArtistsItem(): Item {
        return categoryItems.find { it.name == "artists" }!!
    }

    fun getPrimaryArtistMenu(): Menu {
        return menuRepositoryMapper.mapToMenu(
            MenuDataStoreFixtures.getArtistMenu()
        )
    }
    fun getArtistMenu():Menu{
         return dataResult.find { it.name == "artists" }
             ?.copy(children = artists)!!
    }

    fun getArtistItems(): List<Item> {
        return artists
    }
    fun getSecondArtistItem():Item{
        return artists[1]
    }
    fun getSecondArtistMenu():Menu{
        return Menu(
            artists[selectedItem].name,
            tracks
        )
    }

    fun getArtistsMenu(): Menu {
        return  dataResult.find { it.name == "artists" }!!.copy(children = artists)
    }
fun getPrimaryAlbumsItem():Item{
    return categoryItems.find { it.name == "albums" }!!
}
    fun getTracksMenu():Menu{
        return  dataResult.find { it.name == artists[selectedItem].name }!!.copy(children = tracks)
    }

    fun getPrimaryTracksMenu():Menu{
        return menuRepositoryMapper.mapToMenu(
            MenuDataStoreFixtures.getTrackMenu()
        )
    }
    fun getPrimaryTracksItem():Item{
        return menuRepositoryMapper.mapToItem(
            MenuDataStoreFixtures.getTrackMenu()
        )
    }

    fun getTracksItems():List<Item>{
        return tracks
    }
    fun getSecondTrackItem():Item{
        return tracks[selectedItem]
    }
}