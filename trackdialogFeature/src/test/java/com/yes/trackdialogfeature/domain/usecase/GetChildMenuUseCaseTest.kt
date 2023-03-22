package com.yes.trackdialogfeature.domain.usecase


import com.yes.trackdialogfeature.data.repository.RepositoryFixtures
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.repository.MenuRepository
import com.yes.trackdialogfeature.utils.CoroutineRule
import io.mockk.*

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions

@ExperimentalCoroutinesApi
class GetChildMenuUseCaseTest {
    private val menuRepository: MenuRepository = mockk(relaxed = true)

    ///////////////
    /* private val menuDataStore: MenuDataStore = mockk()
     private val mediaRepository: AudioDataStore = mockk()*/
    //private lateinit var getChildMenuUseCase : GetChildMenuUseCase
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val cut = GetChildMenuUseCase(
        testDispatcher,
        menuRepository
    )

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
    fun `getMenu with 0 param returns DomainResultSuccess `() = runTest {
        // Given
        val expected = DomainFixtures.getCategoriesMenu()
        every {
            menuRepository.getMenu()
        } returns expected
        // When
        val actual = cut(
            GetChildMenuUseCase.Params(0, "")
        )
        // Assert
        verify(exactly = 1) { menuRepository.getMenu() }
        Assertions.assertInstanceOf(DomainResult.Success::class.java, actual)
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)
    }

    @Test
    fun test_get_child_menu_error() = runTest {
        // Given
        val expected = DomainResult.Error(MenuException.UnknownException)
        every { menuRepository.getMenu() } throws Exception()

        // When
        val actual = cut(GetChildMenuUseCase.Params(0, ""))

        // Assert
        assert(actual is DomainResult.Error)
        Assertions.assertInstanceOf(DomainResult.Error::class.java, actual)
        assert((expected as DomainResult.Error).exception == (actual as DomainResult.Error).exception)

    }
    @Test
    fun `getMenu with valid not 0 id param returns DomainResultSuccess `() = runTest {
        // Given
        val expected = DomainFixtures.getAlbumsMenu()
        every {
            menuRepository.getMenu(2, "albums")
        } returns expected
        // When
        val actual = cut(
            GetChildMenuUseCase.Params(2, "albums")
        )
        // Assert
        verify(exactly = 1) { menuRepository.getMenu(2, "albums") }
        Assertions.assertInstanceOf(DomainResult.Success::class.java, actual)
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)

    }
}








