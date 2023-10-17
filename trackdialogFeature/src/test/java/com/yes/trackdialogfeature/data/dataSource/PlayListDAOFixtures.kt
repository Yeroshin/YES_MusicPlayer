package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.UiFixtures
import com.yes.core.repository.entity.TrackEntity

object PlayListDAOFixtures {
    private val selectedItem= UiFixtures.getSelectedArtistIndex()
    private val trackEntities =
        MediaDataStoreFixtures.getTracksAudio().map {
            TrackEntity(
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


    fun getTracks(): List<TrackEntity> {
        return trackEntities
    }
    fun getSelectedTracks():List<TrackEntity>{
        return listOf(trackEntities[selectedItem])
    }
}