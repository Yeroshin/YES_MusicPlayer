package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.model.MenuUi.ItemUi

object  PresentationFixtures {
    val onClick: (TrackDialogContract.Event) -> Unit ={}
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
    //TODO
  /*  fun getArtistMenu():MenuUi{
        val artistMenuDomain=DomainFixtures.getArtistsMenu()
        val artistMenuItemsUi=artistMenuDomain.children.map {
            ItemUi(
                it.id,
                it.name,
0,
                false,
                onClick,


            )
        }
        return MenuUi(
            "artists",
            listOf(

            )
        )
    }*/
    fun getArtistMenuWithBackItem():MenuUi{
        return MenuUi(
            "artists",
            listOf(

            )
        )
    }
}