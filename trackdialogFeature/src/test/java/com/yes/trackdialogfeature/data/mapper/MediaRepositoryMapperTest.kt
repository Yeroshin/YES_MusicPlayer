package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.musicplayer.data.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.domain.models.Track
import org.junit.Assert.*

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MediaRepositoryMapperTest {
    private val cut = MediaRepositoryMapper()

    @ParameterizedTest
    @MethodSource("mapToItemData")
    fun map(
        dataStoreEntity: com.yes.musicplayer.data.entity.MediaDataStoreEntity,
        expectedDomain: Item
    ) {
        val actual = cut.map(
            dataStoreEntity
        )
        assert(actual == expectedDomain)
    }

    @ParameterizedTest
    @MethodSource("mapToTrackData")
    fun mapToTrack(
        dataStoreEntity: com.yes.musicplayer.data.entity.MediaDataStoreEntity,
        expectedDomain: Track
    ) {
        val actual = cut.mapToTrack(
            dataStoreEntity
        )
        assert(actual == expectedDomain)
    }

    companion object {
        @JvmStatic
        fun mapToTrackData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MediaDataStoreFixtures.getTracksAudio()[0],
                    DomainFixtures.getTracksAudio()[0]
                )
            )
        }

        @JvmStatic
        fun mapToItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MediaDataStoreFixtures.getArtists()[0],
                    Item(
                        -1,
                        MediaDataStoreFixtures.getArtists()[0].title,
                        null,
                        false
                    )
                ),
            )
        }
    }
}