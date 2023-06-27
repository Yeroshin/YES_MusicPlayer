package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
object MenuRepositoryFixturesOLD {
    fun getRootMenu(): Menu {
        return Menu(
            "categories",
            listOf(
                Item(
                    "artists",
                    1
                ),
                Item(
                    "albums",
                    2
                ),
                Item(
                    "tracks",
                    3
                )
            )
        )
    }
    fun getChildMenu():Menu{
        return Menu(
            "artists",
            listOf()
        )
    }
    fun getRootItems():List<Item>{
        return listOf(
            Item(
                "artists",
                1
            ),
            Item(
                "albums",
                2
            ),
            Item(
                "tracks",
                3
            )
        )
    }
}