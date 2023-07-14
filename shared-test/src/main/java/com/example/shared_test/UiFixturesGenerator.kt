package com.example.shared_test

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

import kotlin.random.Random

object UiFixturesGenerator {
    private val onClick: (TrackDialogContract.Event) -> Unit = {}
    fun generateParentMenuUi(count: Int): MenuUi {

        val artists = SharedFixtureGenerator.generateArtists(count)
        val items = mutableListOf<MenuUi.ItemUi>()
        for (i in 0 until count) {
            val itemUi = MenuUi.ItemUi(
                i,
                artists[i],
                Random.nextInt(1, 4),
                false,
                TrackDialogContract.Event.OnItemClicked(
                    i,
                    artists[0],
                ),
                onClick
            )
            items.add(itemUi)
        }
        return MenuUi(
            "Categories",
            items
        )
    }

    fun generateChildUiModel(menu: MenuUi): MenuUi {
        val childMenu = menu.copy()
        childMenu.items.toMutableList().add(
            0, MenuUi.ItemUi(
                0,
                "",
                Random.nextInt(1, 4),
                false,
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