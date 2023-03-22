package com.yes.trackdialogfeature.presentation

import app.cash.turbine.test
import com.yes.trackdialogfeature.data.repository.RepositoryFixtures
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuDomainUiMapper

import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import com.yes.trackdialogfeature.utils.CoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Test

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule


class TrackDialogViewModelTest {
    @get:Rule
    val mainCoroutineRule = CoroutineRule()
    @MockK
    private lateinit var getChildMenuUseCase: GetChildMenuUseCase
    private val menuDomainUiMapper = MenuDomainUiMapper()
    private lateinit var cut: TrackDialogViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        cut = TrackDialogViewModel(
            getChildMenuUseCase,
            menuDomainUiMapper
        )
    }

    @Test
    fun `loads menu success`() = runTest {
        val rootMenu=RepositoryFixtures.getCategoriesMenu()
        cut.uiState.test {
            cut.setEvent(TrackDialogContract.Event.OnGetRootMenu)
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
            val expected = awaitItem()
            val expectedData = (expected.trackDialogState as TrackDialogContract.TrackDialogState.Success).menu
            assert(expected==
                TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Success(
                        menuDomainUiMapper.map(rootMenu,::setEvent)
                    )
                )
            )
            cancelAndIgnoreRemainingEvents()
        }

    }


}