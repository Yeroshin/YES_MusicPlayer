package com.yes.trackdialogfeature.presentation

import app.cash.turbine.test
import com.example.shared_test.UiFixturesGenerator
import com.yes.trackdialogfeature.data.repository.RepositoryFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCaseOLD
import com.yes.trackdialogfeature.domain.usecase.SaveTrackToPlaylistUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi

import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import com.yes.trackdialogfeature.utils.CoroutineRule
import io.mockk.*
import org.junit.Test

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.*


class TrackDialogViewModelTest {
    @get:Rule
    val mainCoroutineRule = CoroutineRule()

    // @MockK
    private var getChildMenuUseCaseOLD: GetChildMenuUseCaseOLD = mockk()
    private val menuUiDomainMapper: MenuUiDomainMapper = mockk()
    private lateinit var cut: TrackDialogViewModel
    private val menuStack: ArrayDeque<MenuUi> = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        cut = TrackDialogViewModel(
            getChildMenuUseCaseOLD,
            menuUiDomainMapper,
            menuStack
        )
    }

  /*  @Test
    fun `loads root menu success`() = runTest {
        val domainMenu = RepositoryFixtures.getCategoriesMenu()
        val uiMenu = UiFixturesGenerator.generateParentMenuUi(5)
        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Success(
                uiMenu
            )
        )
        // Given
        coEvery {
            getChildMenuUseCaseOLD(GetChildMenuUseCaseOLD.Params(0, ""))
        } returns DomainResult.Success(domainMenu)
        every {
            menuUiDomainMapper.map(any(), any())
        } returns uiMenu
        every {
            menuStack.isEmpty()
        } returns true
        every {
            menuStack.offer(any())
        } returns true
        cut.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
        cut.uiState.test {
            // When

            // Expect Resource.Idle from initial state
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            // Expect Resource.Loading
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            coVerify(exactly = 1) {
                getChildMenuUseCaseOLD(
                    GetChildMenuUseCaseOLD.Params(
                        0,
                        ""
                    )
                )
            }
            // Expect Resource.Success
            assert(
                awaitItem() == expected
            )
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `loads child menu success`() = runTest {
        val parentMenu = UiFixturesGenerator.generateParentMenuUi(5)
        val childMenu = UiFixturesGenerator.generateChildUiModel(parentMenu)
        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Success(
                parentMenu
            )
        )
        // Given
        coEvery {
            getChildMenuUseCaseOLD(GetChildMenuUseCaseOLD.Params(0, ""))
        } returns DomainResult.Success(RepositoryFixtures.generateMenuDomain(5))
        every {
            menuUiDomainMapper.map(any(), any())
        } returns childMenu
        every {
            menuStack.isEmpty()
        } returns false
        every {
            menuStack.offer(any())
        } returns true
        cut.uiState.test {
            // When
            cut.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
            // Expect Resource.Idle from initial state
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            // Expect Resource.Loading
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            // Expect Resource.Success
            assert(
                awaitItem() == expected
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loads parent menu success`() = runTest {
        val parentMenu = UiFixturesGenerator.generateParentMenuUi(5)

        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Success(
                parentMenu
            )
        )
        // Given
        coEvery {
            getChildMenuUseCaseOLD(GetChildMenuUseCaseOLD.Params(0, ""))
        } returns DomainResult.Success(RepositoryFixtures.generateMenuDomain(5))
        every {
            menuStack.removeLast()
        } returns parentMenu

        cut.setEvent(TrackDialogContract.Event.OnItemBackClicked)
        cut.uiState.test {
            // When

            // Expect Resource.Idle from initial state
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )

            // Expect Resource.Success
            assert(
                awaitItem() == expected
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loads menu memory error`() = runTest {
        val domainMenu = RepositoryFixtures.generateMenuDomain(5)
        val menu = UiFixturesGenerator.generateParentMenuUi(5)
        val expectedState = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
        val expectedEffect = TrackDialogContract.Effect.UnknownException
        // Given
        coEvery {
            getChildMenuUseCaseOLD(GetChildMenuUseCaseOLD.Params(0, ""))
        } returns DomainResult.Success(domainMenu)
        every {
            menuUiDomainMapper.map(any(), any())
        } returns menu
        every {
            menuStack.isEmpty()
        } returns true
        every {
            menuStack.offer(any())
        } returns false
        // When
        cut.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
        cut.uiState.test {


            // Expect Resource.Idle from initial state
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            // Expect Resource.Loading
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            // Expect Resource.Success
            assert(
                awaitItem() == expectedState
            )
            cancelAndIgnoreRemainingEvents()
        }
        cut.effect.test {
            assert(
                awaitItem() == expectedEffect
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loads root menu unknown error`() = runTest {
        val expected = TrackDialogContract.Effect.UnknownException
        // Given
        coEvery {
            getChildMenuUseCaseOLD(GetChildMenuUseCaseOLD.Params(0, ""))
        } returns DomainResult.Error(RepositoryFixtures.getUnknownError())
        /* every {
             menuUiDomainMapper.map(any(),any())
         } returns PresentationFixtures.getUiModel()*/
        cut.uiState.test {
            // When
            cut.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
            // Expect Resource.Idle from initial state
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            // Expect Resource.Loading
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            cancelAndIgnoreRemainingEvents()
        }
        cut.effect.test {
            assert(
                awaitItem() == expected
            )
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `loads root menu empty error`() = runTest {
        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
        // Given
        coEvery {
            getChildMenuUseCaseOLD(GetChildMenuUseCaseOLD.Params(0, ""))
        } returns DomainResult.Error(RepositoryFixtures.getError())
        /* every {
             menuUiDomainMapper.map(any(),any())
         } returns PresentationFixtures.getUiModel()*/
        cut.uiState.test {
            // When
            cut.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
            // Expect Resource.Idle from initial state
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            // Expect Resource.Loading
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            cancelAndIgnoreRemainingEvents()
        }


    }

    @Test
    fun `saves tracks to playlist`() = runTest {
        cut.setEvent(TrackDialogContract.Event.OnItemOkClicked)
       /* coVerify(exactly = 1) {
             saveTracksToPlaylistUseCase(
                 SaveTrackToPlaylistUseCase.Params(
                    1,
                    ""
                )
            )
        }*/
    }*/


}