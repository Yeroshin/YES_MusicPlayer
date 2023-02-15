package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.utils.TestDataGenerator
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException
import io.mockk.coEvery
import io.mockk.mockk

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GetChildMenuUseCaseTest {
    private val menuRepository = mockk< MenuRepository>()
    ///////////////
    private val menuDataStore: MenuDataStore = mockk()
    private val mediaRepository: IMediaRepository= mockk()
    private val menuRep =  MenuRepository(
        menuDataStore,
        mediaRepository
    )
    /////////////
    private lateinit var getChildMenuUseCase : GetChildMenuUseCase
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getChildMenuUseCase = GetChildMenuUseCase(
            testDispatcher,
            menuRepository
        )
    }
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
    @Test
    fun test_get_child_menu_success() = runTest {
        // Given
        val rootMenu = TestDataGenerator.generateRootMenu()
        val expected = Result.Success(rootMenu)
        coEvery {  menuRepository.getMenu() } returns expected

        // When
        val result = getChildMenuUseCase(GetChildMenuUseCase.Params("root",""))

        // Assert
        if (result is Result.Success) assertEquals(result, expected)
        else assertTrue { false }

    }
    @Test
    fun test_get_child_menu_error() = runTest {
        // Given
        val expected = Result.Error<UseCaseException>(UseCaseException.UnknownException)
        coEvery {  menuRepository.getMenu() } throws Exception()

        // When
        val result = getChildMenuUseCase(GetChildMenuUseCase.Params("root",""))

        // Assert
        assertEquals( result , expected )


    }
}