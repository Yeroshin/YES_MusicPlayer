package com.yes.trackdialogfeature.domain.usecase

import com.example.shared_test.UiFixtures
import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.SettingsFixtures
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.domain.models.Track
import com.yes.core.domain.models.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
import com.yes.core.domain.repository.ISettingsRepository
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@OptIn(ExperimentalCoroutinesApi::class)
class SaveTracksToPlaylistUseCaseTest {

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private lateinit var cut: SaveTracksToPlaylistUseCase
    private val mediaRepositoryImpl: MediaRepositoryImpl = mockk()
    private val playListRepository: IPlayListDao = mockk()
    private val settingsRepository: ISettingsRepository = mockk()
    private val menuRepository: IMenuRepository = mockk()

    @BeforeEach
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        cut = SaveTracksToPlaylistUseCase(
            testDispatcher,
            mediaRepositoryImpl,
            playListRepository,
            settingsRepository,
            menuRepository
        )
    }

    @ParameterizedTest
    @MethodSource("runData")
    fun run(
        params: Params?,
        expected: DomainResult<List<Long>>,
        audioItems: List<Track>?,
        primaryItem: Item?,
        savedAudio: List<Track>
    ) = runTest {
        every {
            settingsRepository.getCurrentPlayListName()
        } returns SettingsFixtures.getPlayListName()

        params?.let {
            audioItems?.let {
                every {
                    menuRepository.getItem(params.items[UiFixtures.getSelectedArtistIndex()].id)
                } returns primaryItem

                every {
                    mediaRepositoryImpl.getAudioItems(
                        primaryItem?.type,
                        params.items[UiFixtures.getSelectedArtistIndex()].name
                    )
                } returns audioItems
            }

        }

        every {
            playListRepository.saveTracks(savedAudio)
        } returns listOf(1)
        val actual = cut(
            params
        )
        assert(expected == actual)
        verify(exactly = 1) {
            playListRepository.saveTracks(savedAudio)
        }
    }

    companion object {
        private val mapper: UiMapper = UiMapper()

        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Params(
                        UiFixtures.getArtistsMenuUi().items.map {
                            mapper.map(it)
                        }
                    ),
                    DomainResult.Success(true),
                    MediaDataStoreFixtures.getSelectedTracksAudio(),
                    DomainFixtures.getPrimaryArtistItem(),
                    MediaDataStoreFixtures.getSelectedTracksAudio().map {
                        it.copy(
                            playlistName = SettingsFixtures.getPlayListName()
                        )
                    },
                ),
                arrayOf(
                    Params(
                        listOf(UiFixtures.getNetworkTrack()).map {
                            mapper.map(it)
                        }
                    ),
                    DomainResult.Success(true),
                    null,
                    null,
                    listOf(
                        Track(
                        playlistName = SettingsFixtures.getPlayListName(),
                        title = UiFixtures.getNetworkTrack().name,
                        uri = UiFixtures.getNetworkTrack().name,
                    )
                    ),
                )
            )
        }
    }
}