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
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(Parameterized::class)
internal class GetMenuUseCaseTest(
    private val params: ParamsFactory.Param,
    private val rootMenuParam: MenuFactory.Param,
    private val childMenuParam: MenuFactory.Param,
    private val expectedParam: MenuFactory.Param
) {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val menuRepository: IMenuRepository = mockk(relaxed = true)
    private val cut = GetMenuUseCase(
        testDispatcher,
        menuRepository
    )

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    @Test
    fun `run`() = runTest {
        val rootMenu = MenuFactory.create(rootMenuParam)
        val childMenu = MenuFactory.create(childMenuParam)
        val param = ParamsFactory.create(params)
        val expected=DomainResult.Success(MenuFactory.create(expectedParam))
        every {
            menuRepository.getRootMenu()
        } returns DomainResult.Success(rootMenu)
        every {
            menuRepository.getChildMenu(params.id, params.name)
        } returns DomainResult.Success(childMenu)
        val actual = cut(
            param
        )
        // Assert
        assert(expected == actual)
    }


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    ParamsFactory.Param(0, ""),
                    MenuFactory.Param("Categories", listOf()),
                    MenuFactory.Param("Artist", listOf()),
                    MenuFactory.Param("Categories", listOf())
                ),
                arrayOf(
                    ParamsFactory.Param(1, "Artists"),
                    MenuFactory.Param("Categories", listOf()),
                    MenuFactory.Param("Artists", listOf()),
                    MenuFactory.Param("Artists", listOf())
                )
            )
        }
    }


        object ParamsFactory {
            fun create(param: Param): Params {
                return Params(param.id, param.name)
            }

            data class Param(
                val id: Int,
                val name: String
            )
        }

        object MenuFactory {
            fun create(param: Param): Menu {
                return Menu(param.name, param.children)
            }

            data class Param(
                val name: String,
                val children: List<Menu.Item>
            )
        }

        object MenuItemFactory {
            fun create(param: Param): Item {
                return Item(param.name, param.id)
            }

            data class Param(
                val name: String,
                val id: Int
            )
        }


    }