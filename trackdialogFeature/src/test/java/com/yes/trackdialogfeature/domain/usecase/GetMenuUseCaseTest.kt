package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource



internal class GetMenuUseCaseTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val menuRepository: IMenuRepository = mockk(relaxed = true)
    private val cut = GetMenuUseCase(
        testDispatcher,
        menuRepository
    )

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    @ParameterizedTest
    @MethodSource("testData")
    fun `run`(params: Params, result: Menu) = runTest {
        val expected = DomainResult.Success(result)
        every {
            menuRepository.getRootMenu()
        } returns expected
        val actual = cut(
            params
        )
        // Assert
        assert(expected == actual)
    }

    object MenuProvider {
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
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Params> {
            val testData = listOf(
                TestDatum(1, "Object 1"),
                TestDatum(2, "Object 2"),
                TestDatum(3, "Object 3"),
                TestDatum(4, "Object 4"),
                TestDatum(5, "Object 5")
            )
            return testData.map { ParamsFactory.create(it.id, it.name) }
            // return testData.map { ParamsFactory.create(it.id, it.name) }
        }
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


}