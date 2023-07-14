package com.yes.trackdialogfeature.presentation

import app.cash.turbine.test
import com.example.shared_test.UiFixturesGenerator
import com.yes.core.Fixture
import com.yes.trackdialogfeature.MyTmp
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.DomainResultFactory
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Tempr
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCaseOLD
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase

import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
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
import org.junit.jupiter.api.Test
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
    private val menuUiDomainMapper: MenuUiDomainMapper = mockk()
    private val menuStack: ArrayDeque<MenuUi> = mockk()
    private lateinit var cut: TrackDialogViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher: TestDispatcher = StandardTestDispatcher()

    val tmp: MyTmp = spyk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() = runTest {
        // @MockK
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        cut = TrackDialogViewModel(
            getMenuUseCase,
            menuUiDomainMapper,
            menuStack,
            tmp
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
        } returns menuUiFixture.result
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
                    TrackDialogContract.TrackDialogState.Success(menuUiFixture.result)
                )
            )
            // cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun tmp() {
        every {
            tmp.mytest()
        } returns Tempr(Menu("default", listOf()))
        cut.tmp()
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
    ) = runTest {
        coEvery {
            tmp.mytest()
        } returns DomainResultFactory.createSuccess(Menu("default", listOf()))

        val menu = DomainFixtures.getCategoriesMenu()
           coEvery {
              getMenuUseCase(any())
           } returns  DomainResult.Success(menu)
        coEvery {
            menuUiDomainMapper.map(any(), any())
        } returns menuUiFixture.result
        coEvery {
            menuStack.isEmpty()
        } returns isEmptyFixture.result
        coEvery {
            menuStack.offer(any())
        } returns offerFixture.result
        // When
        cut.setEvent(
            TrackDialogContract.Event.OnItemClicked(
                inputFixture.params["id"] as Int,
                inputFixture.params["name"] as String,
            )
        )

        cut.uiState.test {
            assert(
                awaitItem() == TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )
            assert(
                awaitItem() == stateFixture.result
            )
            // cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        @JvmStatic
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        mapOf(
                            "id" to 0,
                            "name" to ""
                        ),
                        Unit
                    ),
                    Fixture(
                        mapOf(),
                        DomainFixtures.getCategoriesMenu()
                    ),
                    Fixture(
                        mapOf(),
                        UiFixturesGenerator.generateParentMenuUi(5)
                    ),
                    Fixture(
                        mapOf(),
                        true
                    ),
                    Fixture(
                        mapOf(),
                        true
                    ),
                    Fixture(
                        mapOf(),
                        TrackDialogContract.TrackDialogState.Success(PresentationFixtures.getCategoriesMenu())
                    ),
                ),

                )
        }

        @JvmStatic
        fun getParentMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        mapOf(),
                        PresentationFixtures.getCategoriesMenu()
                    )
                )
            )
        }
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