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
    //junit4
    /* @get:Rule
     val mainCoroutineRule = CoroutineRule()*/
    //junit5

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
        menuUiFixture: Fixture<MenuUi>
    ) = runTest {
        coEvery {
            menuStack.removeLast()
        } returns menuUiFixture.data
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
                    TrackDialogContract.TrackDialogState.Success(menuUiFixture.data)
                )
            )
            // cancelAndIgnoreRemainingEvents()
        }
    }

    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(
        inputFixture: Fixture<Unit>,
        menuDomainFixture: Fixture<Menu>,
        menuUiFixture: Fixture<MenuUi>,
        isEmptyFixture: Fixture<Boolean>,
        offerFixture: Fixture<Boolean>,
        stateFixture: Fixture<TrackDialogContract.State>
        // stateFixture: Fixture<MenuUi>
    ) = runTest {

        val menu = DomainFixtures.getCategoriesMenu()
        coEvery {
            getMenuUseCase(any())
        } returns DomainResult.Success(menu)
        coEvery {
            uiMapper.map(any(), any())
        } returns menuUiFixture.data
        coEvery {
            menuStack.isEmpty()
        } returns isEmptyFixture.data
        coEvery {
            menuStack.offer(any())
        } returns offerFixture.data
        // When
        cut.setEvent(
            TrackDialogContract.Event.OnItemClicked(
                0,
                "",
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
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            assert(
                awaitItem() == stateFixture.data
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @ParameterizedTest
    @MethodSource("saveItemsData")
    fun saveItems(
        itemsUiFixture: Fixture<List<MenuUi.ItemUi>>,
        itemsDomainFixture: Fixture<List<Menu.Item>>
    ) = runTest {
        every {
            uiMapper.map(any())
        } returnsMany itemsDomainFixture.data
        coEvery {
            saveTracksToPlaylistUseCase(any())
        } returns DomainResult.Success(true)
        cut.setEvent(
            TrackDialogContract.Event.OnItemOkClicked(
                itemsUiFixture.data
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
                        itemsDomainFixture.data
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
                    Fixture(
                        PresentationFixtures.getCategoriesMenu().items.mapIndexed { index, item ->
                            if (index == 1) {
                                item.copy(selected = true)
                            } else {
                                item
                            }
                        }
                    ),
                    Fixture(
                        DomainFixtures.getCategoryItems().mapIndexed { index, item ->
                            if (index == 1) {
                                item.copy(selected = true)
                            } else {
                                item
                            }
                        }
                    ),
                )
            )
        }

        @JvmStatic
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(

                        Unit
                    ),
                    Fixture(

                        DomainFixtures.getCategoriesMenu()
                    ),
                    Fixture(

                        PresentationFixtures.getCategoriesMenu()
                    ),
                    Fixture(
                        true
                    ),
                    Fixture(
                        true
                    ),
                    Fixture(
                        TrackDialogContract.State(
                            TrackDialogContract.TrackDialogState.Success(
                                PresentationFixtures.getCategoriesMenu()
                            )
                        )
                    ),
                ),

                )
        }

        @JvmStatic
        fun getParentMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        PresentationFixtures.getCategoriesMenu()
                    )
                )
            )
        }
    }

}