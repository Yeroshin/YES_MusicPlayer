package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.DataSourceFixtures
import com.yes.trackdialogfeature.data.RepositoryFixtures
import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify


import org.junit.Assert.assertEquals
import org.junit.Test

class MenuRepositoryImplTest {
    private val menuDataStore: MenuDataStore = mockk(relaxed = true)
    private val audioDataStore: AudioDataStore = mockk(relaxed = true)
    private val menuMapper: MenuMapper = MenuMapper()
    private val cut = MenuRepositoryImpl(
        menuDataStore,
        audioDataStore,
        menuMapper
    )

    @Test
    fun `1 getMenu handles api success and returns root MenuApiModel`() {
        // Given
        val expected = RepositoryFixtures.getRootMenu()
        every { menuDataStore.findRoot() } returns DataSourceFixtures.findRoot()
        every { menuDataStore.getChildren(any()) } returns DataSourceFixtures.getRootChildren()
        // When
        val actual = cut.getMenu()
        // Assert
        verify { menuDataStore.findRoot() }
        assertEquals(expected.type, actual.type)
        assertEquals(expected.children.elementAt(0).name, actual.children.elementAt(0).name)
        assertEquals(expected.children.elementAt(1).name, actual.children.elementAt(1).name)
        /* assert(actual is Result.Success)
         assertEquals((actual as Result.Success).data, expected)*/
    }

    @Test
    fun `2 get artists returns all artists`() {
        // Given
        val expected = RepositoryFixtures.getArtistMenu()
        every {
            menuDataStore.getChildren("artists")
        } returns DataSourceFixtures.getRootChildren()
        every {
            audioDataStore.getMediaItems(
                arrayOf("artists"),
                null,
                null
            )
        } returns DataSourceFixtures.getArtistsChildren()

        // When
        val actual = cut.getMenu("artists", null)
        // Assert
        verify { menuDataStore.getChildren(any())}
        verify { audioDataStore.getMediaItems(any(),any(),any())}
        assertEquals(expected.type, actual.type)
        assertEquals(expected.children.elementAt(0).name, actual.children.elementAt(0).name)
        assertEquals(expected.children.elementAt(1).name, actual.children.elementAt(1).name)
        assertEquals(expected.children.elementAt(1).type, actual.children.elementAt(1).type)
        /* assert(actual is Result.Success)
         assertEquals((actual as Result.Success).data, expected)*/
    }

    @Test
    fun `3 get artist returns artist tracks & artist albums`() {
        // Given
        val expected = RepositoryFixtures.getArtistMenu()
        every {
            menuDataStore.getChildren("artists")
        } returns DataSourceFixtures.getRootChildren()
      /*  every {
            audioDataStore.getMediaItems(
                arrayOf("artists"),
                null,
                null
            )
        } returns DataSourceFixtures.getArtistsChildren()*/

        // When
        val actual = cut.getMenu("artists", null)
        // Assert
        verify { menuDataStore.getChildren(any())}
        verify(exactly = 0) { audioDataStore.getMediaItems(any(),any(),any())}
        assertEquals(expected.type, actual.type)
        assertEquals(expected.children.elementAt(0).name, actual.children.elementAt(0).name)
        assertEquals(expected.children.elementAt(1).name, actual.children.elementAt(1).name)
        assertEquals(expected.children.elementAt(1).type, actual.children.elementAt(1).type)
        /* assert(actual is Result.Success)
         assertEquals((actual as Result.Success).data, expected)*/
    }


}