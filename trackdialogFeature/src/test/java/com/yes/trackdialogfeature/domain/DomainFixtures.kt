package com.yes.trackdialogfeature.domain

import com.example.shared_test.SharedFixtureGenerator
import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.entity.Track

object DomainFixtures {
    fun getEmptyError(): MenuException {
        return MenuException.Empty
    }

    fun getUnknownError(): DomainResult.DomainException {
        return DomainResult.UnknownException
    }

    private const val count = 5
    private val selectedItem = SharedFixtureGenerator.getSelectedItem()
    private val artists =
        MediaDataStoreFixtures.getArtists().map {
            Item(
                -1,
                it.title,
                null,
                false
            )
        }
    private val tracks =
        MediaDataStoreFixtures.getTracksMedia().map {
            Item(
                -1,
                it.title,
                null,
                false
            )
        }
    private val tracksAudio = MediaDataStoreFixtures.getTracksAudio().map {
        Track(
            null,
            "",
            it.artist,
            it.title,
            it.data,
            it.duration,
            it.album,
            it.size,
        )
    }
    fun getTracksAudio():List<Track>{
        return tracksAudio
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
        return dataResult.find { it.name == "categories" }!!.copy(children = categoryItems)
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

    fun getArtistMenu(): Menu {
       return dataResult.find { it.name == "artists" }
            ?.copy(children = artists)!!
    }



    fun getArtistItemsWithSelectedItem(): List<Item> {
        val selectedArtist = artists.mapIndexed { index, item ->
            if (index == selectedItem) {
                item.copy(selected = true)
            } else {
                item
            }
        }
        return selectedArtist
    }

    fun getSecondArtistItem(): Item {
        return artists[1]
    }

    fun getSelectedTracksItems(): List<Item> {
        val selectedArtists = artists.toMutableList()
        selectedArtists[selectedItem] = selectedArtists[selectedItem].copy(selected = true)
        return selectedArtists
    }

    fun getArtistsMenu(): Menu {
        val artists= getArtistsItems().map { it.copy(menuId = 4, type = "artist") }
        return dataResult.find { it.name == "artists" }!!.copy(children = artists)
    }
    fun getArtistsItems(): List<Item> {
        return artists
    }

    fun getPrimaryAlbumsItem(): Item {
        return categoryItems.find { it.name == "albums" }!!
    }

    fun getSelectedArtistTracksMenu(): Menu {
        val tracks=tracks.map { it.copy(menuId =7, type = "track" ) }
        return dataResult.find { it.name == artists[selectedItem].name }!!.copy(children = tracks)
    }
    fun getSelectedArtistTracksItems():List<Item>{
        return tracks
    }

    fun getPrimaryTracksMenu(): Menu {
        return menuRepositoryMapper.mapToMenu(
            MenuDataStoreFixtures.getTrackMenu()
        )
    }

    fun getPrimaryTracksItem(): Item {
        return menuRepositoryMapper.mapToItem(
            MenuDataStoreFixtures.getTrackMenu()
        )
    }

    fun getTracksItems(): List<Item> {
        return tracks
    }

    fun getSelectedTrackItem(): Item {
        return tracks[selectedItem]
    }
    fun getSelectedArtistItem():Item{
        return artists[selectedItem].copy(menuId = 4, type ="artist" )
    }
    fun getTracksFromSelectedArtist():List<Track>{
        return tracksAudio.filter {
            it.artist== artists[selectedItem].name
        }
    }

}