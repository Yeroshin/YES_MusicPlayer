package com.yes.trackdialogfeature.data

import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel

object DataSourceFixtures {
    fun getRootMenuApiModel(): MenuApiModel {
        val item1 = MenuApiModel(
            "artists",
            "artists",
            listOf()
        )
        val item2 = MenuApiModel(
            "albums",
            "albums",
            listOf()
        )
        return MenuApiModel(
            "categories",
            "categories",
            listOf(item1,item2)
        )
    }
    fun getArtistsMenu(): MenuApiModel {
        val item1 = MenuApiModel(
            "artistTracks",
            "Dire Straits",
            listOf()
        )
        val item2 = MenuApiModel(
            "artistTracks",
            "Chris Rea",
            listOf()
        )
        return MenuApiModel(
            "artists",
            null,
            listOf(item1,item2)
        )
    }
    fun getTracksMenu(): MenuApiModel {
        val item1 = MenuApiModel(
            "",
            "Money For Nothing",
            listOf()
        )
        val item2 = MenuApiModel(
            "",
            "Sultans of Swing",
            listOf()
        )
        return MenuApiModel(
            "artistTracks",
            "Dire Straits",
            listOf(item1,item2)
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
    fun getArtistsChildren():Set<String>{
        return setOf<String>(
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
            listOf()
        )
        val item2 = MenuApiModel(
            "albums",
            "albums",
            listOf()
        )
        return MenuApiModel(
            "categories",
            "categories",
            listOf(item1,item2)
        )
    }
    fun getArtistsMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "artist",
            "Chris Rea",
            listOf()
        )
        val item2 = MenuApiModel(
            "artist",
            "Dire Straits",
            listOf()
        )
        return MenuApiModel(
            "artists",
            "artists",
            listOf(item1,item2)
        )
    }
    fun getArtistMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "artistTracks",
            "artistTracks",
            listOf()
        )
        val item2 = MenuApiModel(
            "artistAlbums",
            "artistAlbums",
            listOf()
        )
        return MenuApiModel(
            "artist",
            "artist",
            listOf(item1,item2)
        )
    }
    fun getArtistAlbumsMenu(): MenuApiModel{
        val item1 = MenuApiModel(
            "albums",
            "Dire Straits",
            listOf()
        )
        val item2 = MenuApiModel(
            "albums",
            "Dire Straits",
            listOf()
        )
        return MenuApiModel(
            "artistAlbums",
            "artistAlbums",
            listOf(item1,item2)
        )
    }

}