package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher


import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


internal class GetMenuUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val menuRepository: MenuRepositoryImpl = mockk()
    private val mediaRepositoryImpl: MediaRepositoryImpl = mockk()
    private lateinit var cut: GetMenuUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() = runTest {
        // @MockK
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        cut = GetMenuUseCase(
            testDispatcher,
            menuRepository,
            mediaRepositoryImpl
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("runData")
    fun run(
        params: Params?,
        expected: DomainResult<Menu>,
        rootMenu: Menu?,
        currentItem: Item?,
        childPrimaryItem: Item?,
        childItems: List<Item>?
    ) = runTest {
        params?.let {
            currentItem?.let {
                coEvery {
                    menuRepository.getItem(params.id)
                } returns currentItem
            }
            coEvery {
                menuRepository.getChildItem(params.id)
            } returns childPrimaryItem
            childItems?.let {
                childPrimaryItem?.let {
                    childPrimaryItem.type?.let { childPrimaryItemType ->
                        currentItem?.let {
                            coEvery {
                                mediaRepositoryImpl.getMenuItems(
                                    childPrimaryItem.id,
                                    childPrimaryItemType,
                                    currentItem.type,
                                    params.name
                                )
                            } returns childItems
                        }
                    }
                }
            }

        } ?: run {
            coEvery {
                menuRepository.getRootMenu()
            } returns rootMenu
        }


        val actual = cut(
            params
        )
        // Assert
        assert(expected == actual)
    }


    companion object {
        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                 arrayOf(
                     null,
                     DomainResult.Error(DomainFixtures.getUnknownError()),
                     null,
                     null,
                     null,
                     null
                 ),
                 arrayOf(
                     null,
                     DomainResult.Success(DomainFixtures.getCategoriesMenu()),
                     DomainFixtures.getCategoriesMenu(),
                     null,
                     null,
                     null
                 ),
                 arrayOf(
                     Params(
                         DomainFixtures.getArtistsItem().id,
                         DomainFixtures.getArtistsItem().name
                     ),
                     DomainResult.Error(DomainFixtures.getUnknownError()),
                     null,
                     DomainFixtures.getPrimaryArtistsItem(),
                     DomainFixtures.getPrimaryAlbumsItem(),
                     null
                 ),
                  arrayOf(
                      Params(
                          DomainFixtures.getArtistsItem().id,
                          DomainFixtures.getArtistsItem().name
                      ),
                      DomainResult.Success(
                          DomainFixtures.getArtistMenu()
                      ),
                      null,
                      DomainFixtures.getPrimaryArtistsItem(),
                      DomainFixtures.getPrimaryArtistItem(),
                      DomainFixtures.getArtistItems(),
                  ),
                  arrayOf(
                      Params(
                          DomainFixtures.getSecondArtistItem().id,
                          DomainFixtures.getSecondArtistItem().name
                      ),
                      DomainResult.Success(
                          DomainFixtures.getTracksMenu()
                      ),
                      null,
                      DomainFixtures.getSecondArtistItem(),
                      DomainFixtures.getPrimaryTracksItem(),
                      DomainFixtures.getTracksItems()
                  ),
                arrayOf(
                    Params(
                        DomainFixtures.getSecondTrackItem().id,
                        DomainFixtures.getSecondTrackItem().name
                    ),
                    DomainResult.Error(
                        DomainFixtures.getEmptyError()
                    ),
                    null,
                    DomainFixtures.getPrimaryTracksItem(),
                    null,
                    null
                ),
            )
        }
    }


}