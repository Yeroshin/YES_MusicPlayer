package com.example.shared_test

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi

import kotlin.random.Random

object UiFixtures {
    private const val networkPath = "http://23.111.104.132/chil96.aacp"

    private const val selectedArtist=1
    private const val selectedArtistSelectedTrack=1
    private val onClick: (TrackDialogContract.Event) -> Unit = {}

    fun getArtistsMenuUi(): MenuUi {

        val artists = SharedFixtureGenerator.getArtistsNames()
        val items = mutableListOf<MenuUi.ItemUi>()
        for (i in artists.indices) {
            val itemUi = MenuUi.ItemUi(
                i,
                artists[i],
                Random.nextInt(1, 4),
                i== selectedArtist,
                TrackDialogContract.Event.OnItemClicked(
                    i,
                    artists[0],
                ),
                onClick
            )
            items.add(itemUi)
        }
        return MenuUi(
            "artists",
            items
        )
    }
    fun getSelectedArtistIndex():Int{
        return selectedArtist
    }
    fun getSelectedArtistSelectedTrack():Int{
        return selectedArtistSelectedTrack
    }
    fun getBackItem(): MenuUi.ItemUi {
        return MenuUi.ItemUi(
            -1,
            "..",
            0,
            null,
            TrackDialogContract.Event.OnItemBackClicked,
            onClick
        )
    }
    fun getNetworkPath(): String {
        return networkPath
    }
    fun getNetworkTrack(): MenuUi.ItemUi {
        return MenuUi.ItemUi(
            name=networkPath,
            selected = true
        )
    }


}