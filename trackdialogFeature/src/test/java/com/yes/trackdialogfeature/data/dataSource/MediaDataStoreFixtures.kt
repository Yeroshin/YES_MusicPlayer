package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.SharedFixtureGenerator
import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import kotlin.random.Random

object MediaDataStoreFixtures {
    private const val count = 5
    private val albums =
        SharedFixtureGenerator.generateMediaItemNames(count).map {
            MediaDataStoreEntity(
                it,
                //  0,
                "",
                "",
                0,
                "",
                0
            )
        }
    private val artists =
        SharedFixtureGenerator.generateMediaItemNames(count).map {
            MediaDataStoreEntity(
                it,
                "",
                "",
                0,
                "",
                0
            )
        }
    private val tracks =
        SharedFixtureGenerator.generateMediaItemNames(count).map {
            MediaDataStoreEntity(
                it,
                "",
                "",
                0,
                "",
                0
            )
        }

    fun getCount(): Int {
        return count
    }

    fun getArtistsListMedia(): List<MediaDataStoreEntity> {
        return artists
    }

    fun getTracksListMedia(): List<MediaDataStoreEntity> {
        return tracks
    }

    fun getTracksList(): List<MediaDataStoreEntity> {
        return tracks.mapIndexed { index, element ->
            element.copy(
                element.title,
                artists[index].title,
                albums[index].title,
                Random.nextLong(135000, 270000),
                "/storage/emulated/0/media/${element.title}.mp3",
                Random.nextLong(4000000, 8000000)
            )
        }

    }
}