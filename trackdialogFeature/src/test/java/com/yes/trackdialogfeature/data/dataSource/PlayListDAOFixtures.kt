package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.UiFixtures
import com.yes.core.data.entity.PlayListDataBaseTrackEntity

object PlayListDAOFixtures {
    private val selectedItem= UiFixtures.getSelectedArtistIndex()
    private val trackEntities =
        MediaDataStoreFixtures.getTracksAudio().map {
            PlayListDataBaseTrackEntity(
                null,
                "",
                it.artist,
                it.title,
                it.data,
                it.duration,
                it.album,
                it.size
            )
        }


    fun getTracks(): List<PlayListDataBaseTrackEntity> {
        return trackEntities
    }
    fun getSelectedTracks():List<PlayListDataBaseTrackEntity>{
        return listOf(trackEntities[selectedItem])
    }
}