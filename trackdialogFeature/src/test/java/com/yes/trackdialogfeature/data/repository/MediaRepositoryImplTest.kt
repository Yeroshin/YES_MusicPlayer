package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.repository.entity.PlayListDataBaseTrackEntity
import com.yes.core.repository.data.dataSource.MediaDataStore
import com.yes.core.repository.entity.MediaDataStoreEntity
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MediaRepositoryImplTest {
    private val mediaDataStore: MediaDataStore = mockk()
    private val mediaRepositoryMapper: MediaRepositoryMapper = mockk()
    private lateinit var cut: MediaRepositoryImpl

    @BeforeEach
    fun setUp() {
        // @MockK
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        cut = MediaRepositoryImpl(
            mediaDataStore,
            mediaRepositoryMapper
        )
    }

    @Suppress("UNCHECKED_CAST")
    @ParameterizedTest
    @MethodSource("getAudioItemsData")
    fun getAudioItems(
        expected: List<Item>,
        inputParam: Map<String, Any?>,
        param: Map<String, Any?>,
        mediaItemsEntity: List<MediaDataStoreEntity>,
        mediaItemsDomain: List<PlayListDataBaseTrackEntity>
    ) {
        every {
            mediaDataStore.getAudioItems(
                param["selection"] as String?,
                param["selectionArgs"] as Array<String>
            )
        } returns mediaItemsEntity
        every {
            mediaRepositoryMapper.mapToTrack(
                any()
            )
        } returnsMany mediaItemsDomain
        val actual = cut.getAudioItems(
            inputParam["selectionType"] as String?,
            inputParam["selectionName"] as String
        )
        assert(expected == actual)
    }

    @Suppress("UNCHECKED_CAST")
    @ParameterizedTest
    @MethodSource("getMenuItemsData")
    fun getMenuItems(
        expected: List<Item>,
        inputParam: Array<String?>,
        param: Array<Any>,
        mediaItemsEntity: List<MediaDataStoreEntity>,
        mediaItemsDomain: List<Item>
    ) {
        every {
            mediaDataStore.getMediaItems(
                param[0] as String,
                param[1] as String?,
                param[2] as Array<String>
            )
        } returns mediaItemsEntity
        every {
            mediaRepositoryMapper.map(
                any()
            )
        } returnsMany mediaItemsDomain
        val actual = cut.getMenuItems(
            inputParam[0] as String,
            inputParam[1],
            inputParam[2] as String
        )
        assert(expected == actual)
    }

    companion object {
        @JvmStatic
        fun getAudioItemsData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainFixtures.getTracksFromSelectedArtist(),
                    mapOf(
                        "selectionType" to "artist",
                        "selectionName" to DomainFixtures.getSelectedArtistItem().name
                    ),
                    mapOf(
                        "selection" to "artist",
                        "selectionArgs" to arrayOf(DomainFixtures.getSelectedArtistItem().name)
                    ),
                    MediaDataStoreFixtures.getTracksFromSelectedArtist(),
                    DomainFixtures.getTracksFromSelectedArtist()
                ),
                arrayOf(
                    DomainFixtures.getTracksFromSelectedArtist(),
                    mapOf(
                        "selectionType" to null,
                        "selectionName" to DomainFixtures.getSelectedArtistItem().name
                    ),
                    mapOf(
                        "selection" to null,
                        "selectionArgs" to emptyArray<String>()
                    ),
                    MediaDataStoreFixtures.getTracksFromSelectedArtist(),
                    DomainFixtures.getTracksFromSelectedArtist()
                )
            )
        }

        @JvmStatic
        fun getMenuItemsData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainFixtures.getArtistsItems(),
                    arrayOf(
                        "artist",
                        null,
                        "artists"
                    ),
                    arrayOf(
                        "artist",
                        null,
                        emptyArray<String>()
                    ),
                    MediaDataStoreFixtures.getArtists(),
                    DomainFixtures.getArtistsItems()
                ),
                arrayOf(
                    DomainFixtures.getSelectedArtistTracksItems(),
                    arrayOf(
                        "track",
                        "artist",
                        DomainFixtures.getSelectedArtistItem().name
                    ),
                    arrayOf(
                        "track",
                        "artist",
                        arrayOf(DomainFixtures.getSelectedArtistItem().name)
                    ),
                    MediaDataStoreFixtures.getTracksFromSelectedArtist(),
                    DomainFixtures.getSelectedArtistTracksItems()
                ),
            )
        }
    }
}