package com.yes.trackdialogfeature.data

import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel

object DataSourceFixtures {
    fun getRootMenuApiModel(): MenuApiModel {
        val item1 = MenuApiModel(
            "artists",
            "artists",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "albums",
            "albums",
            arrayOf()
        )
        return MenuApiModel(
            "categories",
            "categories",
            arrayOf(item1,item2)
        )
    }
    fun getArtistsMenu(): MenuApiModel {
        val item1 = MenuApiModel(
            "artistTracks",
            "Dire Straits",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "artistTracks",
            "Chris Rea",
            arrayOf()
        )
        return MenuApiModel(
            "artists",
            null,
            arrayOf(item1,item2)
        )
    }
    fun getTracksMenu(): MenuApiModel {
        val item1 = MenuApiModel(
            "",
            "Money For Nothing",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "",
            "Sultans of Swing",
            arrayOf()
        )
        return MenuApiModel(
            "artistTracks",
            "Dire Straits",
            arrayOf(item1,item2)
        )
    }
//////////////////////////////
    //////////////////////////
    ///////////////////////////
    fun findRoot(): String{
        return "categories"
    }
    fun getRootChildren():Array<String>{
        return arrayOf(
            "artists",
            "albums"
        )
    }
    fun getArtistsChildren():Array<String>{
        return arrayOf(
            "artists",
            "albums"
        )
    }

}
object RepositoryFixtures {
    fun getRootMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "artists",
            "artists",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "albums",
            "albums",
            arrayOf()
        )
        return MenuApiModel(
            "categories",
            "categories",
            arrayOf(item1,item2)
        )
    }
    fun getArtistsMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "artist",
            "Chris Rea",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "artist",
            "Dire Straits",
            arrayOf()
        )
        return MenuApiModel(
            "artists",
            "artists",
            arrayOf(item1,item2)
        )
    }
    fun getArtistMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "artistTracks",
            "Dire Straits",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "artistAlbums",
            "Dire Straits",
            arrayOf()
        )
        return MenuApiModel(
            "artist",
            "Dire Straits",
            arrayOf(item1,item2)
        )
    }
    fun getArtistAlbumsMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "album",
            "Brothers in Arms",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "album",
            "Love over Gold",
            arrayOf()
        )
        return MenuApiModel(
            "artistAlbums",
            "Dire Straits",
            arrayOf(item1,item2)
        )
    }
    fun getArtistAlbumTrackMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "track",
            "So Far Away",
            arrayOf()
        )
        val item2 = MenuApiModel(
            "track",
            "Walk of Life",
            arrayOf()
        )
        return MenuApiModel(
            "album",
            "Brothers in Arms",
            arrayOf(item1,item2)
        )
    }

}