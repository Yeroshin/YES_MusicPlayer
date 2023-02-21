package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.DataFixtures
import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.common.Result
import io.mockk.mockk
import io.mockk.verify

import org.junit.jupiter.api.Test
import org.junit.Assert.assertEquals

class MenuRepositoryImplTest {
    private val menuDataStore: MenuDataStore = mockk()
    private val audioDataStore: AudioDataStore = mockk()
    private val menuMapper:MenuMapper = MenuMapper()
    private val cut = MenuRepositoryImpl(
        menuDataStore,
        audioDataStore,
        menuMapper
    )

    @Test
    fun `getMenu handles api success and returns root MenuApiModel`() {
        // Given
        val expected = Result.Success(DataFixtures.getRootMenu())

        // When
        val actual = cut.getMenu()
        // Assert
        verify { menuDataStore.getRoot() }
        assert(actual is Result.Success)
        assertEquals((actual as Result.Success).data, expected)
    }

    @Test
    fun `getMenu handles api success and returns artists MenuApiModel`() {
        //Given
        val expected = Result.Success(DataFixtures.getChildMenu())
        //When
        val actual = cut.getMenu("artists", null)
        //Assert
        verify { menuDataStore.getChild("artists") }
        verify {
            audioDataStore.getMediaItems(
                arrayOf("artists"),
                null,
                null
            )
        }
        assert(actual is Result.Success)
        assertEquals((actual as Result.Success).data, expected)
    }


}