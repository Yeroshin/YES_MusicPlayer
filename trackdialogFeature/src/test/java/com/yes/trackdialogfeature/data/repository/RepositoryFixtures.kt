package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException

object RepositoryFixtures {

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
    }

    fun getError():MenuException{
        return MenuException.Empty
    }
    fun getUnknownError(): DomainResult.DomainException {
        return DomainResult.UnknownException
    }

}