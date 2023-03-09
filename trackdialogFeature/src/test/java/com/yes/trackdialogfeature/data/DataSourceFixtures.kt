package com.yes.trackdialogfeature.data

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

object DataSourceFixtures {
    fun getRootMenuApiModel(): MenuDataStoreEntity {
      /*  val item1 = MenuDataStoreEntity(
            "artists",
            "artists",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "albums",
            "albums",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "categories",
            "categories",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }
    fun getArtistsMenu(): MenuDataStoreEntity {
       /* val item1 = MenuDataStoreEntity(
            "artistTracks",
            "Dire Straits",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "artistTracks",
            "Chris Rea",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "artists",
            null,
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }
    fun getTracksMenu(): MenuDataStoreEntity {
       /* val item1 = MenuDataStoreEntity(
            "",
            "Money For Nothing",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "",
            "Sultans of Swing",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "artistTracks",
            "Dire Straits",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
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

    fun getRootMenu(): MenuDataStoreEntity{

       /* val item1 = MenuDataStoreEntity(
            "artists",
            "artists",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "albums",
            "albums",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "categories",
            "categories",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }
    fun getArtistsMenu(): MenuDataStoreEntity{
       /* val item1 = MenuDataStoreEntity(
            "artist",
            "Chris Rea",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "artist",
            "Dire Straits",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "artists",
            "artists",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }
    fun getArtistMenu(): MenuDataStoreEntity{
        /*val item1 = MenuDataStoreEntity(
            "artistTracks",
            "Dire Straits",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "artistAlbums",
            "Dire Straits",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "artist",
            "Dire Straits",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }
    fun getArtistAlbumsMenu(): MenuDataStoreEntity{
        /*val item1 = MenuDataStoreEntity(
            "album",
            "Brothers in Arms",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "album",
            "Love over Gold",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "artistAlbums",
            "Dire Straits",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }
    fun getArtistAlbumTrackMenu(): MenuDataStoreEntity{
       /* val item1 = MenuDataStoreEntity(
            "track",
            "So Far Away",
            arrayOf()
        )
        val item2 = MenuDataStoreEntity(
            "track",
            "Walk of Life",
            arrayOf()
        )
        return MenuDataStoreEntity(
            "album",
            "Brothers in Arms",
            arrayOf(item1,item2)
        )*/
        TODO("Not yet implemented")
    }

}