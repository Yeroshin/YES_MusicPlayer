package com.yes.trackdialogfeature.presentation

import app.cash.turbine.test
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.repository.RepositoryFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper

import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import com.yes.trackdialogfeature.utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Test

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule


class TrackDialogViewModelTest {
    @get:Rule
    val mainCoroutineRule = CoroutineRule()

    @MockK
    private var getChildMenuUseCase: GetChildMenuUseCase = mockk()
    private val menuUiDomainMapper: MenuUiDomainMapper = mockk()
    private lateinit var cut: TrackDialogViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        cut = TrackDialogViewModel(
            getChildMenuUseCase,
            menuUiDomainMapper
        )
    }

    @Test
    fun `loads root menu success`() = runTest {
        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Success(
                PresentationFixtures.getUiModel()
            )
        )
        // Given
        coEvery {
            getChildMenuUseCase(GetChildMenuUseCase.Params(0, ""))
        } returns DomainResult.Success(RepositoryFixtures.getCategoriesMenu())
        every {
            menuUiDomainMapper.map(any(), any())
        } returns PresentationFixtures.getUiModel()
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
    fun `loads root menu unknown error`() = runTest {
        val expected = TrackDialogContract.Effect.UnknownException
        // Given
        coEvery {
            getChildMenuUseCase(GetChildMenuUseCase.Params(0, ""))
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
            getChildMenuUseCase(GetChildMenuUseCase.Params(0, ""))
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


}