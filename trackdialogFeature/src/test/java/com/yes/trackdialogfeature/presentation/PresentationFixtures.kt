package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.presentation.model.MenuUi

object  PresentationFixtures {
    fun onClick(){

    }
    fun getUiModel(): MenuUi {
        return MenuUi(
            "categories",
            listOf(
                MenuUi.MediaItem(
                    1,
                    "artists",
                    1,
                    { }
                ),
                MenuUi.MediaItem(
                    2,
                    "albums",
                1,
                ) {},
                MenuUi.MediaItem(
                    3,
                    "tracks",
                    1
                ) {}
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