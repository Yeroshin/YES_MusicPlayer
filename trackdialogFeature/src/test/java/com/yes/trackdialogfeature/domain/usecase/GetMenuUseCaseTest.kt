package com.yes.trackdialogfeature.domain.usecase

import com.example.shared_test.SharedFixtureGenerator
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.domain.DomainFixtures
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
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.runner.RunWith


internal class GetMenuUseCaseTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val menuRepository: IMenuRepository = mockk()
    private val mediaRepositoryImpl:MediaRepositoryImpl= mockk()
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
        menu: Menu?
    ) = runTest {

        input
            ?.let {
                coEvery {
                    menuRepository.getChildMenu(input.id, input.name)
                } returns menu
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
                    DomainResult.Error(DomainResult.UnknownException),
                    null
                ),
                arrayOf(
                    Params(
                        DomainFixtures.getArtistsItem().id,
                        DomainFixtures.getArtistsItem().name
                    ),
                    DomainResult.Error(DomainResult.UnknownException),
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
                   DomainFixtures.getArtistItem()
                ),
            )
        }
    }


}