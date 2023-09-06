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
import io.mockk.every
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
    private val mediaRepository: MediaRepositoryImpl = mockk()
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
            mediaRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ParameterizedTest
    @MethodSource("runData")
    fun run(
        params: Params?,
        expected: DomainResult<Menu>,
        currentMenu: Item?,
        childMenu: Item?,
        childItems: List<Item>?,
        rootMenu: Menu?,
    ) = runTest {


       params?.let {
            every {
                menuRepository.getItem(params.id)
            }returns currentMenu
            every {
                menuRepository.getChildItem(params.id)
            }returns childMenu
            currentMenu?.let {
                childMenu?.let {
                    childItems?.let {
                        every {
                            mediaRepository.getMenuItems(
                                childMenu.type!!,
                                currentMenu.type,
                                params.name
                            )
                        } returns childItems
                    }
                }
            }
        }?:run {
            every {
                menuRepository.getRootMenu()
            }returns rootMenu
        }

        val actual = cut(
            params
        )
////////////////////////////////////
        val menu=DomainFixtures.getSelectedArtistTracksMenu()
        val newItems=menu.children
        val items=DomainFixtures.getSelectedArtistTracksItems().map { it.copy(id =7, type = "track") }
        assert(newItems==items)
        ////////////////////////////////////
        val expect= expected
val tmp2=actual

        // Assert
       assert(expect == actual)
    }


    companion object {
        @JvmStatic
        fun runData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Success(
                        DomainFixtures.getArtistsMenu()
                    ),
                    DomainFixtures.getPrimaryArtistsItem(),
                    DomainFixtures.getPrimaryArtistItem(),
                    DomainFixtures.getArtistsItems(),
                    null
                ),
                arrayOf(
                    Params(
                        DomainFixtures.getSelectedArtistItem().id,
                        DomainFixtures.getSelectedArtistItem().name
                    ),
                    DomainResult.Success(
                        DomainFixtures.getSelectedArtistTracksMenu()
                    ),
                    DomainFixtures.getPrimaryArtistItem(),
                    DomainFixtures.getPrimaryArtistTrackItem(),

                    DomainFixtures.getSelectedArtistTracksItems(),
                    null
                ),
                arrayOf(
                    null,
                    DomainResult.Success(
                        DomainFixtures.getCategoriesMenu()
                    ),
                    null,
                    null,
                    null,
                    DomainFixtures.getCategoriesMenu()
                ),
                arrayOf(
                    null,
                    DomainResult.Error(DomainResult.UnknownException),
                    null,
                    null,
                    null,
                    null
                ),
            )
        }
    }


}