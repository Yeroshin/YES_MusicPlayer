package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.SettingsFixtures
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.entity.Track
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
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

    @BeforeEach
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        cut = SaveTracksToPlaylistUseCase(
            testDispatcher,
            mediaRepositoryImpl,
            playListRepository,
            settingsRepository
        )
    }

    @ParameterizedTest
    @MethodSource("runData")
    fun run(
        params: Params?,
        expected: DomainResult<List<Long>>,
        audio: List<Track>,
        savedAudio: List<Track>
    ) = runTest {
        every {
            settingsRepository.getCurrentPlayListName()
        } returns SettingsFixtures.getPlayListName()

        params?.let {
            every {
                mediaRepositoryImpl.getAudioItems(
                    params.items[1].type,
                    params.items[1].name
                )
            } returns audio
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
        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Params(DomainFixtures.getSelectedTracksItems()),
                    DomainResult.Success(true),
                    MediaDataStoreFixtures.getSelectedTracksAudio(),
                    MediaDataStoreFixtures.getSelectedTracksAudio().map {
                        it.copy(
                            playlistName = SettingsFixtures.getPlayListName()
                        )
                    },
                )
            )
        }
    }
}