package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.model.MenuUi.ItemUi

object PresentationFixtures {
    val onClick: (TrackDialogContract.Event) -> Unit = {}
    fun getCategoriesMenu(): MenuUi {

        return MenuUi(
            "categories",
            listOf(
                ItemUi(
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
                ItemUi(
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
                ItemUi(
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

    private val uiMapper = UiMapper()
    fun getArtistMenu(): MenuUi {
        return uiMapper.map(
            DomainFixtures.getArtistsMenu(),
            onClick
        )
    }

    fun getArtistMenuWithBackItem(): MenuUi {
        /* val menu = getArtistMenu()
         menu.items.toMutableList().add(
             0,
             ItemUi(
                 -1,
                 "..",
                 0,
                 null,
                 TrackDialogContract.Event.OnItemBackClicked,
                 onClick
             )
         )
         return menu*/
        val menu = getArtistMenu()
        val items=menu.items.toMutableList()
            items.add(
            0,
            ItemUi(
                -1,
                "..",
                0,
                null,
                TrackDialogContract.Event.OnItemBackClicked,
                onClick
            )
        )
        return menu.copy(items = items)
      /*  return menu.copy(
            items = items
        )*/
    }
}