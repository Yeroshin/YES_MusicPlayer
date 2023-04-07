package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

object  PresentationFixtures {
    val onClick: (TrackDialogContract.Event) -> Unit ={}
    fun getUiModel(): MenuUi {

        return MenuUi(
            "categories",
            listOf(
                MenuUi.MediaItem(
                    1,
                    "artists",
                    1,
                    onClick
                ),
                MenuUi.MediaItem(
                    2,
                    "albums",
                1,
                    onClick
                ),
                MenuUi.MediaItem(
                    3,
                    "tracks",
                    1,
                    onClick
                )
            )
        )
    }

    fun getDomainModel():Menu{
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
}