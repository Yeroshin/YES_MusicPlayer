package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.utils.TestDataGenerator
import com.yes.trackdialogfeature.domain.common.Result
import io.mockk.coEvery

import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GetChildMenuUseCaseTest {
    private val menuRepository: MenuRepository = mockk()
    private lateinit var getChildMenuUseCase : GetChildMenuUseCase
    @Before
    fun setUp() {

        getChildMenuUseCase = GetChildMenuUseCase(
            menuRepository
        )
    }
    @Test
    fun test_get_child_menu_success() = runTest {

        val rootMenu = TestDataGenerator.generateRootMenu()
        val menu = Result.Success(rootMenu)

        // Given
        every {  menuRepository.getMenu() } returns menu

        // When
        val result = getChildMenuUseCase(GetChildMenuUseCase.Params("root",""))

        // Assert
        if (result is Result.Success) assertEquals(result.data, menu)
        else assertTrue { false }

    }
}