package com.yes.trackdialogfeature.domain.usecase

import com.example.shared_test.SharedFixtureGenerator
import com.yes.trackdialogfeature.domain.IMediaRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
import com.yes.trackdialogfeature.utils.CoroutineRule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher


import org.junit.runners.Parameterized
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(Parameterized::class)
internal class GetMenuUseCaseTest(
    private val params: Params,
    private val resultMenuRepository: Menu,
    private val resultMediaRepository: Item,
    private val expected: DomainResult<Menu>
) {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val menuRepository: IMenuRepository = mockk(relaxed = true)
    private val mediaRepository: IMediaRepository = mockk(relaxed = true)
    private val cut = GetMenuUseCase(
        testDispatcher,
        menuRepository,
        mediaRepository
    )

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    @Test
    fun `run`() = runTest {
        every {
            menuRepository.getRootMenu()
        } returns DomainResult.Success(resultMenuRepository)
        every {
            menuRepository.getChildMenu(params.id)
        } returns DomainResult.Success(resultMenuRepository)
        every {
            mediaRepository.getMedia()
        } returns DomainResult.Success(resultMediaRepository)
        val actual = cut(
            params
        )
        // Assert
        assert(expected == actual)
    }

    /* companion object MenuProvider {
         @JvmStatic
         fun testData() = listOf(
             Arguments.of(
                 Params(0, ""),
                 Menu(
                     "Categories",
                     listOf(
                         Menu.Item(
                             "artists",
                             1
                         ),
                         Menu.Item(
                             "albums",
                             2
                         ),
                         Menu.Item(
                             "tracks",
                             3
                         )
                     )
                 )
             )

         )
     }*/

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<Any?>> {
            val menuItems=SharedFixtureGenerator.generateArtists(3)
                .map {
                    MenuItemFactory.create(it, 4)
                }
            return listOf(
                arrayOf(
                    Params(0, ""),
                    Menu(
                        "Categories",
                        listOf(
                            Menu.Item(
                                "artists",
                                1
                            ),
                            Menu.Item(
                                "albums",
                                2
                            ),
                            Menu.Item(
                                "tracks",
                                3
                            )
                        )
                    ),
                    menuItems,
                    DomainResult.Success(
                        Menu(
                            "Categories",
                            listOf(
                                Menu.Item(
                                    "artists",
                                    1
                                ),
                                Menu.Item(
                                    "albums",
                                    2
                                ),
                                Menu.Item(
                                    "tracks",
                                    3
                                )
                            )
                        )
                    )
                ),
                arrayOf(
                    Params(1, "artists"),
                    Menu(
                        "artist",
                        listOf()
                    ),
                    menuItems,
                    DomainResult.Success(
                        Menu(
                            "artist",
                            menuItems
                        )
                    )
                )
            )
        }
        // return testData.map { ParamsFactory.create(it.id, it.name) }
    }

    data class TestDatum(val id: Int, val name: String)
    object ParamsFactory {
        fun create(id: Int, name: String): Params {
            return Params(id, name)
        }
    }

    object MenuFactory {
        fun create(name: String, children: List<Menu.Item>): Menu {
            return Menu(name, children)
        }
    }

    object MenuItemFactory {
        fun create(name: String, id: Int): Item {
            return Item(name, id)
        }
    }


}