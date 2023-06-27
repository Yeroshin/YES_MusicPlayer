package com.yes.trackdialogfeature.data.repository

import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.dataSource.AudioDataStoreFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException

object RepositoryFixtures {
    ///////old
    /* fun generateMenuDomain(count:Int):Menu{
         val titles = SharedFixtureGenerator.generateArtists(count)
         val items = mutableListOf<Menu.Item>()
         for (i in 1 until count) {
             val mediaItem = Menu.Item(
                 titles[i],
                 Random.nextInt(1, 4)
             )
             items.add(mediaItem)
         }
         return Menu(
             titles[0],
             items
         )
     }
     fun getCategoriesMenu(): Menu {
         return Menu(
                 "categories",
                 listOf(
                     Menu.Item(
                         "artists",
                         1
                     ),
                     Menu.Item(
                         "albums",
                         2,
                     ),
                     Menu.Item(
                         "tracks",
                         3,
                     )
                 )
             )

     }

     fun getArtistsMenu(): Menu {
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
         return Menu(
             "artists",
             listOf(
                 Menu.Item(
                     "Dire Straits",
                     3
                 ),
                 Menu.Item(
                     "Chris Rea",
                     3
                 )
             )
         )
     }

     fun getArtistMenu(): Menu {
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
         return Menu(
             "Dire Straits",
             listOf(
                 Menu.Item(
                     "artistAlbums",
                     4
                 ),
                 Menu.Item(
                     "artistTracks",
                     5
                 )
             )
         )
     }

     fun getAlbumsMenu(): Menu {
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
         return Menu(
                 "albums",
                 listOf(
                     Menu.Item(
                         "Brothers in Arms",
                         5
                     ),
                     Menu.Item(
                         "Love over Gold",
                         5
                     )
                 )
             )
     }

     fun getAlbumTracksMenu(): Menu {
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
         return Menu(
                 "Brothers in Arms",
                 listOf(
                     Menu.Item(
                         "Money for Nothing",
                         8
                     ),
                     Menu.Item(
                         "Your Latest Trick",
                         8
                     )
                 )
             )
     }*/
////////////////////endofold
    fun getError(): MenuException {
        return MenuException.Empty
    }

    fun getUnknownError(): DomainResult.DomainException {
        return DomainResult.UnknownException
    }

    private const val count = 5
    private val artists = AudioDataStoreFixtures.getArtists().result.map { Menu.Item(it.name, 4) }
    private val tracks = AudioDataStoreFixtures.getTracks().result.map { Menu.Item(it.name, 7) }
    val dataResult = listOf(
        Menu(
            "categories",
            listOf(
                Menu.Item(
                    "artists",
                    1
                ),
                Menu.Item(
                    "albums",
                    2
                ),
                Menu.Item(
                    "tracks",
                    3
                )
            )
        ),
        Menu(
            "artists",
            artists
        ),
        Menu(
            artists[0].name,
            tracks
        ),

        )

    fun getRootDomainMenuParam() {

    }

    fun getRootDomainMenu() {

    }

    fun getArtistsMenuDomain(): Fixture<Menu> {
        val menu = dataResult.find { it.name == "artists" }
        return Fixture(
            mapOf(
                "id" to 0,
                "name" to ""
            ),
            menu!!
        )
    }

    fun getArtistTracks(): Fixture<Menu> {
        val menu = dataResult.find { it.name == artists[0].name }
        return Fixture(
            mapOf(
                "id" to 4,
                "name" to artists[0].name
            ),
            menu!!
        )
    }


}