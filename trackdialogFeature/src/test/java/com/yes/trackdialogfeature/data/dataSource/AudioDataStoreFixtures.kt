package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.SharedFixtureGenerator
import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.repository.RepositoryFixtures
import com.yes.trackdialogfeature.data.repository.entity.AudioDataStoreEntity

object AudioDataStoreFixtures {
    private const val count = 5
    private val artists =
        SharedFixtureGenerator.generateMediaItemNames(count).map { AudioDataStoreEntity(it) }
    private val tracks =
        SharedFixtureGenerator.generateMediaItemNames(count).map { AudioDataStoreEntity(it) }

    fun getArtists(): Fixture<List<AudioDataStoreEntity>> {
        return Fixture(
            mapOf(
                "projection" to "artists",
                "selection" to null,
                "selectionArgs" to null
            ),
            artists
        )
    }

    fun getTracks():Fixture< List<AudioDataStoreEntity>> {
        return Fixture(
            mapOf(
                "projection" to "track",
                "selection" to "artists",
                "selectionArgs" to artists[0].name
            ),
            tracks
        )
    }
}