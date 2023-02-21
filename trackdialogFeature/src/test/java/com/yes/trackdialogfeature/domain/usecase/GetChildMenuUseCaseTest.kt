package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.IMediaDataStore
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException
import com.yes.trackdialogfeature.utils.CoroutineRule
import io.mockk.coEvery
import io.mockk.mockk

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetChildMenuUseCaseTest {
    private val menuRepository = mockk< MenuRepository>()
    ///////////////
    private val menuDataStore: MenuDataStore = mockk()
    private val mediaRepository: AudioDataStore = mockk()
    private val menuRep =  MenuRepository(
        menuDataStore,
        mediaRepository
    )
    /////////////
    private lateinit var getChildMenuUseCase : GetChildMenuUseCase
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

  /*  @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

    }
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }*/
    @Test
    fun test_get_child_menu_success() = runTest {
        // Given
       getChildMenuUseCase = GetChildMenuUseCase(
           testDispatcher,
           menuRepository
       )
        val expected = Result.Success(DomainFixtures.getRootMenu())
        coEvery {  menuRepository.getMenu() } returns expected

        // When
        val result = getChildMenuUseCase(GetChildMenuUseCase.Params("root",""))

        // Assert
        assert(result is Result.Success)
        assertEquals(result, expected)


    }
    @Test
    fun test_get_child_menu_error() = runTest {
        // Given
        getChildMenuUseCase = GetChildMenuUseCase(
            testDispatcher,
            menuRepository
        )
        val expected = Result.Error<UseCaseException>(UseCaseException.UnknownException)
        coEvery {  menuRepository.getMenu() } throws Exception()

        // When
        val result = getChildMenuUseCase(GetChildMenuUseCase.Params("root",""))

        // Assert
        assert(result is Result.Error)
        assertEquals( result , expected )


    }
}