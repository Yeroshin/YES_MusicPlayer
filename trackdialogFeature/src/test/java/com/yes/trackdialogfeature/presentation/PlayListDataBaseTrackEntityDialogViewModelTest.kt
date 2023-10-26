package com.yes.trackdialogfeature.presentation

import app.cash.turbine.test
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.core.domain.models.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException

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
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.*

@ExtendWith(MockKExtension::class)
class PlayListDataBaseTrackEntityDialogViewModelTest {

    private val getMenuUseCase: GetMenuUseCase = mockk()
    private val saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase = mockk()
    private val uiMapper: UiMapper = mockk()
    private val menuStack: ArrayDeque<MenuUi> = mockk()
    private lateinit var cut: TrackDialogViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp()  {
        // @MockK
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        cut = TrackDialogViewModel(
            getMenuUseCase,
            saveTracksToPlaylistUseCase,
            uiMapper,
            menuStack,
            null
        )
    }
    @AfterEach
    fun tearDown() {
        // removing the test dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun dismiss()= runTest{
        cut.setEvent(TrackDialogContract.Event.OnButtonCancelClicked)
        cut.uiState.test{
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
        }
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("getParentMenuData")
    fun getParentMenu(
        menuUiFixture: MenuUi?,
        menuStackFixture:Boolean,
        state:TrackDialogContract.TrackDialogState,
        effect:TrackDialogContract.Effect?
    ) = runTest {
        coEvery {
            menuStack.isEmpty()
        }returns menuStackFixture
        menuUiFixture?.let {
            coEvery {
                menuStack.removeLast()
            } returns menuUiFixture
        }

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
                    TrackDialogContract.TrackDialogState.Loading
                )
            )
            assert(
                awaitItem() == TrackDialogContract.State(
                    state
                )
            )
            // cancelAndIgnoreRemainingEvents()
        }
       cut.effect.test {
            effect?.let {
                assert(
                    awaitItem()==effect
                )

            }
        }
    }

    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(
        inputFixture: TrackDialogContract.Event,
        useCaseParams: GetMenuUseCase.Params?,
        menuDomainFixture: DomainResult<Menu>,
        menuUiFixture: MenuUi?,
        isEmptyFixture: Boolean?,
        offerFixture: Boolean?,
        stateFixture: TrackDialogContract.State,
        effectFixture: TrackDialogContract.Effect?
    ) = runTest {


        coEvery {
            getMenuUseCase(useCaseParams)
        } returns menuDomainFixture
        menuUiFixture?.let {
            coEvery {
                uiMapper.map(any(), any())
            } returns menuUiFixture
        }
        isEmptyFixture?.let {
            coEvery {
                menuStack.isEmpty()
            } returns isEmptyFixture
        }
        offerFixture?.let {
            coEvery {
                menuStack.offer(any())
            } returns offerFixture
        }



        cut.uiState.test {
            // When
            cut.setEvent(
                inputFixture
            )
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
        cut.effect.test {
            effectFixture?.let {
                assert(
                    awaitItem() == effectFixture
                )
            }
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
                    null,
                    DomainResult.Success(DomainFixtures.getCategoriesMenu()),
                    PresentationFixtures.getCategoriesMenu(),
                    true,
                    true,
                    TrackDialogContract.State(
                        TrackDialogContract.TrackDialogState.Success(
                            PresentationFixtures.getCategoriesMenu()
                        )
                    ),
                    null
                ),
                arrayOf(
                    TrackDialogContract.Event.OnItemClicked(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    GetMenuUseCase.Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Success(DomainFixtures.getArtistMenu()),
                    PresentationFixtures.getArtistMenu(),
                    false,
                    true,
                    TrackDialogContract.State(
                        TrackDialogContract.TrackDialogState.Success(
                            PresentationFixtures.getArtistMenuWithBackItem()
                        )
                    ),
                    null
                ),
                arrayOf(
                    TrackDialogContract.Event.OnItemClicked(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    GetMenuUseCase.Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Success(DomainFixtures.getArtistMenu()),
                    PresentationFixtures.getArtistMenu(),
                    true,
                    false,
                    TrackDialogContract.State(
                        TrackDialogContract.TrackDialogState.Idle
                    ),
                    TrackDialogContract.Effect.UnknownException
                ),
                arrayOf(
                    TrackDialogContract.Event.OnItemClicked(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    GetMenuUseCase.Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Error(
                        DomainResult.UnknownException
                    ),
                    null,
                    null,
                    null,
                    TrackDialogContract.State(
                        TrackDialogContract.TrackDialogState.Idle
                    ),
                    TrackDialogContract.Effect.UnknownException
                ),
                arrayOf(
                    TrackDialogContract.Event.OnItemClicked(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    GetMenuUseCase.Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Error(
                        MenuException.Empty
                    ),
                    null,
                    null,
                    null,
                    TrackDialogContract.State(
                        TrackDialogContract.TrackDialogState.Idle
                    ),
                    null
                ),
            )
        }

        @JvmStatic
        fun getParentMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    PresentationFixtures.getCategoriesMenu(),
                    false,
                    TrackDialogContract.TrackDialogState.Success(
                        PresentationFixtures.getCategoriesMenu()
                    ),
                    null
                ),
                arrayOf(
                    null,
                    true,
                    TrackDialogContract.TrackDialogState.Idle,
                    TrackDialogContract.Effect.UnknownException
                ),
            )
        }
    }

}