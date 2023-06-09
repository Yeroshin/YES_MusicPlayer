package com.example.shared_test

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
                artists[i],
                Random.nextInt(1, 4),
                TrackDialogContract.Event.OnItemClicked(
                    i,
                    artists[0],
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

    fun generateChildUiModel(menu: MenuUi): MenuUi {
        val childMenu = menu.copy()
        childMenu.items.toMutableList().add(
            0, MenuUi.MediaItem(
                0,
                "",
                Random.nextInt(1, 4),
                TrackDialogContract.Event.OnItemClicked(
                    0,
                    "",
                ),
                onClick
            )
        )
        return childMenu
    }
}