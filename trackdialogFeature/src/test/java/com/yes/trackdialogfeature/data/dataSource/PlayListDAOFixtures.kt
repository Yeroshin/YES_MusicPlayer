package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.UiFixtures
import com.yes.core.domain.models.Track

object PlayListDAOFixtures {
    private val selectedItem= UiFixtures.getSelectedArtistIndex()
    private val tracks =
        MediaDataStoreFixtures.getTracksAudio().map {
            Track(
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


    fun getTracks(): List<Track> {
        return tracks
    }
    fun getSelectedTracks():List<Track>{
        return listOf(tracks[selectedItem])
    }
}