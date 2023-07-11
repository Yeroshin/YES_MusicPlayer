package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.data.repository.entity.TrackEntity

object PlayListDataBaseFixtures {
    private val tracks =
        MediaDataStoreFixtures.getTracksListMedia().map {
            TrackEntity(
                null,
                SharedPreferencesFixtures.getPlayListName(),
                it.artist,
                it.title,
                it.data,
                it.duration,
                it.album,
                it.size
            )
        }


        fun getTracks():List<TrackEntity>{
        return tracks
    }
}