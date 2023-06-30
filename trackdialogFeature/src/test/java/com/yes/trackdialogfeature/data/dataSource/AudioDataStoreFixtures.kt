package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.SharedFixtureGenerator
import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.repository.entity.AudioDataStoreEntity

object AudioDataStoreFixtures {
    private const val count = 5
    private val artists =
        SharedFixtureGenerator.generateMediaItemNames(count).map { AudioDataStoreEntity(it) }
    private val tracks =
        SharedFixtureGenerator.generateMediaItemNames(count).map { AudioDataStoreEntity(it) }

    fun getArtistsListAudioDataStore(): Fixture<List<AudioDataStoreEntity>> {
        return Fixture(
            mapOf(
                "projection" to arrayOf("artist"),
                "selection" to null,
                "selectionArgs" to emptyArray()
            ),
            artists
        )
    }

    fun getTracksListAudioDataStore():Fixture< List<AudioDataStoreEntity>> {
        return Fixture(
            mapOf(
                "projection" to arrayOf("track"),
                "selection" to "artist",
                "selectionArgs" to arrayOf(artists[0].name)
            ),
            tracks
        )
    }
}