package com.example.shared_test

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi

import kotlin.random.Random

object UiFixtures {
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

}