package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.PlayListDAOFixtures
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Track
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
import io.mockk.MockKAnnotations
import io.mockk.mockk
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
    private val settingsRepository:ISettingsRepository= mockk()

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
        expected:DomainResult<List<Long>>,
        media:List<MediaDataStoreEntity>,
        audio:List<Track>
    ) = runTest{
        val actual = cut(
            params
        )
        assert(expected == actual)
    }

    companion object {
        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainFixtures.getSelectedTracksItems(),
                    PlayListDAOFixtures.getSelectedTracks(),
                    MediaDataStoreFixtures.getSelectedTracksAudio(),
                    PlayListDAOFixtures.getSelectedTracks()
                )
            )
        }
    }
}