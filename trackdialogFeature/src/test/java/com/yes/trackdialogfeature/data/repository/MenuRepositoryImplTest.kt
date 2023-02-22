package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.DataFixtures
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
    fun `getMenu handles api success and returns root MenuApiModel`() {
        // Given
        val expected = DataFixtures.getRootMenuApiModel()
        every { menuDataStore.getRoot() } returns expected
        // When
        val actual = cut.getMenu()
        // Assert
        verify { menuDataStore.getRoot() }
        assertEquals(expected.type, actual.type)
        assertEquals(expected.children.elementAt(0), actual.children.elementAt(0))
        assertEquals(expected.children.elementAt(1), actual.children.elementAt(1))
        /* assert(actual is Result.Success)
         assertEquals((actual as Result.Success).data, expected)*/
    }

    @Test
    fun `get artists returns artists with artist Tracks`() {
        //Given
        val expected = DataFixtures.getArtistsMenu()
        every {
            menuDataStore.getChild(
                "artists"
            )
        } returns "artistTracks"
        every {
            audioDataStore.getMediaItems(
                arrayOf("artists"),
                null,
                null
            )
        } returns setOf("Dire Straits", "Chris Rea")
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
        assertEquals(expected.type, actual.type)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.children.elementAt(0).name, actual.children.elementAt(0).name)
        assertEquals(expected.children.elementAt(1).type, actual.children.elementAt(1).type)
        /* assert(actual is Result.Success)
         assertEquals((actual as Result.Success).data, expected)*/
    }

    @Test
    fun `get artistTracks returns artists with tracks`() {

        //Given
        val expected = DataFixtures.getTracksMenu()
        every { menuDataStore.getChild("artistTracks") } returns ""
        every {
            audioDataStore.getMediaItems(
                arrayOf("artistTracks"),
                "artists",
                arrayOf("Dire Straits")
            )
        } returns setOf("Money For Nothing", "Sultans of Swing")
        //When
        val actual = cut.getMenu("artistTracks", "Dire Straits")
        //Assert
        verify { menuDataStore.getChild("artists") }
        verify {
            audioDataStore.getMediaItems(
                arrayOf("artistTracks"),
                "artists",
                arrayOf("Dire Straits")
            )
        }
        assertEquals(expected.type, actual.type)
        assertEquals(expected.name, actual.name)
        assertEquals(expected.children.elementAt(0).name, actual.children.elementAt(0).name)
        assertEquals(expected.children.elementAt(1).type, actual.children.elementAt(1).type)
        /* assert(actual is Result.Success)
             assertEquals((actual as Result.Success).data, expected)*/
    }


}