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

    @ParameterizedTest
    @MethodSource("runData")
    fun `run`(
        input: Params?,
        expected: DomainResult<Menu>,
        menu: Menu?,
        item: Item?,
        items: List<Item>?
    ) = runTest {

        input
            ?.let {
                coEvery {
                    menuRepository.getChildMenu(input.id)
                } returns menu
                menu?.let {
                    coEvery {
                        menuRepository.getChildItem(input.id)
                    } returns item
                    item?.let {
                        coEvery {
                            mediaRepositoryImpl.getMenuItems(item.id, item.type, input.name)
                        } returns items!!
                    }
                }

            }
            ?: run {
                coEvery {
                    menuRepository.getRootMenu()
                } returns menu
            }

        val actual = cut(
            input
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
                    null
                ),
                arrayOf(
                    null,
                    DomainResult.Success(DomainFixtures.getCategoriesMenu()),
                    DomainFixtures.getCategoriesMenu(),
                    null,
                    null
                ),
                arrayOf(
                    Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Error(DomainFixtures.getUnknownError()),
                    DomainFixtures.getPrimaryArtistsMenu(),
                    null,
                    null
                ),
                arrayOf(
                    Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Success(
                        DomainFixtures.getArtistsMenu()
                    ),
                    DomainFixtures.getPrimaryArtistMenu(),
                    DomainFixtures.getPrimaryArtistItem(),
                    DomainFixtures.getArtistItems()
                ),
                arrayOf(
                    Params(
                        DomainFixtures.getSecondArtistItem().id,
                        DomainFixtures.getSecondArtistItem().name
                    ),
                    DomainResult.Success(
                        DomainFixtures.getTracksMenu()
                    ),
                    DomainFixtures.getPrimaryTracksMenu(),
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
                    null,
                    null
                ),
            )
        }
    }


}