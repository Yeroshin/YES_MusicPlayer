package com.yes.trackdialogfeature

import com.example.shared_test.SharedFixtureGenerator
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

import kotlin.random.Random

object UiFixturesGenerator {
    private val onClick: (TrackDialogContract.Event) -> Unit = {}
    fun generateParentMenuUi(count: Int): MenuUi {

        val artists = SharedFixtureGenerator.generateArtists(count)
        val items = mutableListOf<MenuUi.MediaItem>()
        for (i in 0 until count) {
            val mediaItem = MenuUi.MediaItem(
                i,
                artists[i].name,
                Random.nextInt(1, 4),
                TrackDialogContract.Event.OnItemClicked(
                    i,
                    "artists",
                ),
                onClick
            )
            items.add(mediaItem)
        }
        return MenuUi(
            "Categories",
            items
        )
    }
}