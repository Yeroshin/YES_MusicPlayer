package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

object  PresentationFixtures {
    val onClick: (TrackDialogContract.Event) -> Unit ={}
    fun getCategoriesMenu(): MenuUi {

        return MenuUi(
            "categories",
            listOf(
                MenuUi.ItemUi(
                    1,
                    "artists",
                    1,
                    false,
                    TrackDialogContract.Event.OnItemClicked(
                        1,
                        "artists",
                    ),
                    onClick
                ),
                MenuUi.ItemUi(
                    2,
                    "albums",
                1,
                    false,
                    TrackDialogContract.Event.OnItemClicked(
                        1,
                        "albums",
                    ),
                    onClick
                ),
                MenuUi.ItemUi(
                    3,
                    "tracks",
                    1,
                    false,
                    TrackDialogContract.Event.OnItemClicked(
                        1,
                        "tracks",
                    ),
                    onClick
                )
            )
        )
    }
}