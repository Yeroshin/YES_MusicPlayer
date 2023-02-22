package com.yes.trackdialogfeature.data

import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel

object DataFixtures {
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
            null,
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
}