package com.yes.trackdialogfeature.data

import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel
import com.yes.trackdialogfeature.domain.entity.Menu

object DataFixtures {
    fun getRootMenuApiModel(): MenuApiModel {
        val item1 = MenuApiModel(
            "artists",
            "artists"
        )
        val item2 = MenuApiModel(
            "albums",
            "albums"
        )
        val menu = MenuApiModel(
            "categories",
            null
        )
        menu.children.add(item1)
        menu.children.add(item2)

        return menu
    }
    fun getRootMenu(): Menu {
        val item1 = Menu(
            "artists"
        )
        val item2 = Menu(
            "albums"
        )
        val menu = Menu(
            "categories"
        )
        menu.children.add(item1)
        menu.children.add(item2)

        return menu
    }

    fun getChildMenu():MenuApiModel{
        val item1 = MenuApiModel(
            "artistTracks",
            "Dire Straits"
        )
        val item2 = MenuApiModel(
            "artistTracks",
            "Chris Rea"
        )
        val children = setOf(item1, item2)
        val menu = MenuApiModel(
            "artists",
            null
        )

        return menu
    }
}