package com.yes.trackdialogfeature.presentation

import com.example.shared_test.SharedFixtureGenerator
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import kotlin.random.Random

object  PresentationFixtures {
    val onClick: (TrackDialogContract.Event) -> Unit ={}
    fun getParentUiModel(): MenuUi {

        return MenuUi(
            "categories",
            listOf(
                MenuUi.MediaItem(
                    1,
                    "artists",
                    1,
                    TrackDialogContract.Event.OnItemClicked(
                        1,
                        "artists",
                    ),
                    onClick
                ),
                MenuUi.MediaItem(
                    2,
                    "albums",
                1,
                    TrackDialogContract.Event.OnItemClicked(
                        1,
                        "albums",
                    ),
                    onClick
                ),
                MenuUi.MediaItem(
                    3,
                    "tracks",
                    1,
                    TrackDialogContract.Event.OnItemClicked(
                        1,
                        "tracks",
                    ),
                    onClick
                )
            )
        )
    }
    fun generateChildUiModel(count:Int): MenuUi {
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
            "artists",
            items
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