package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu

object DomainFixturesOLD {


    fun getAlbumsMenu(): DomainResult<Menu> {
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
        return DomainResult.Success(
            Menu(
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
        )
    }
    fun getCategoriesMenu(): DomainResult<Menu> {
        return DomainResult.Success(
            Menu(
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
        )
    }


}