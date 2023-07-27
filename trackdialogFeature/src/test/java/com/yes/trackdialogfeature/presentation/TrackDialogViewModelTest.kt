package com.yes.trackdialogfeature.presentation

import app.cash.turbine.test
import com.yes.core.Fixture
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu

import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi

import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import io.mockk.*
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.*

@ExtendWith(MockKExtension::class)
class TrackDialogViewModelTest {

    private val getMenuUseCase: GetMenuUseCase = mockk()
    private val saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase = mockk()
    private val uiMapper: UiMapper = mockk()
    private val menuStack: ArrayDeque<MenuUi> = mockk()
    private lateinit var cut: TrackDialogViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() = runTest {
        // @MockK
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        cut = TrackDialogViewModel(
            getMenuUseCase,
            saveTracksToPlaylistUseCase,
            uiMapper,
            menuStack,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("getParentMenuData")
    fun getParentMenu(
        menuUiFixture: MenuUi
    ) = runTest {
        coEvery {
            menuStack.removeLast()
        } returns menuUiFixture
        // When
        cut.setEvent(TrackDialogContract.Event.OnItemBackClicked)

        cut.uiState.test {
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Success(menuUiFixture)
                )
            )
            // cancelAndIgnoreRemainingEvents()
        }
    }

    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(
        inputFixture: TrackDialogContract.Event,
        menuDomainFixture: Menu,
        menuUiFixture: MenuUi,
        isEmptyFixture: Boolean,
        offerFixture: Boolean,
        stateFixture: TrackDialogContract.State
    ) = runTest {


        coEvery {
            getMenuUseCase(any())
        } returns DomainResult.Success(menuDomainFixture)
        coEvery {
            uiMapper.map(any(), any())
        } returns menuUiFixture
        coEvery {
            menuStack.isEmpty()
        } returns isEmptyFixture
        coEvery {
            menuStack.offer(any())
        } returns offerFixture
        // When
        cut.setEvent(
            inputFixture
        )

        cut.uiState.test {
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            assert(
                awaitItem() == stateFixture
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @ParameterizedTest
    @MethodSource("saveItemsData")
    fun saveItems(
        itemsUiFixture: List<MenuUi.ItemUi>,
        itemsDomainFixture: List<Menu.Item>
    ) = runTest {
        every {
            uiMapper.map(any())
        } returnsMany itemsDomainFixture
        coEvery {
            saveTracksToPlaylistUseCase(any())
        } returns DomainResult.Success(true)
        cut.setEvent(
            TrackDialogContract.Event.OnButtonOkClicked(
                itemsUiFixture
            )
        )

        cut.uiState.test {
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Dismiss
                )
            )
            coVerify(exactly = 1) {
                saveTracksToPlaylistUseCase(
                    SaveTracksToPlaylistUseCase.Params(
                        itemsDomainFixture
                    )
                )
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        @JvmStatic
        fun saveItemsData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    PresentationFixtures.getCategoriesMenu().items.mapIndexed { index, item ->
                        if (index == 1) {
                            item.copy(selected = true)
                        } else {
                            item
                        }
                    },
                    DomainFixtures.getCategoryItems().mapIndexed { index, item ->
                        if (index == 1) {
                            item.copy(selected = true)
                        } else {
                            item
                        }
                    },
                )
            )
        }

        @JvmStatic
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    TrackDialogContract.Event.OnItemClicked(),
                    DomainFixtures.getCategoriesMenu(),
                    PresentationFixtures.getCategoriesMenu(),
                    true,
                    true,
                    TrackDialogContract.State(
                        TrackDialogContract.TrackDialogState.Success(
                            PresentationFixtures.getCategoriesMenu()
                        )
                    ),
                ),
            )
        }

        @JvmStatic
        fun getParentMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    PresentationFixtures.getCategoriesMenu()
                )
            )
        }
    }

}