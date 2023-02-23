package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.data.DataFixtures
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import org.junit.Assert.assertArrayEquals


import org.junit.Assert.assertEquals
import org.junit.Test

internal class MenuDataStoreTest {
    private val cut= MenuDataStore()
   /* @Test
    fun `getRoot returns root MenuApiModel`() {

        // arrange
        val expected = DataFixtures.getRootMenuApiModel()

        // act
        val actual = cut.getRoot()
        // Assert
        assertEquals(actual.name,expected.name)
        assertEquals(actual.type,expected.type)
        assertEquals(actual.children.elementAt(0),expected.children.elementAt(0))
        assertEquals(actual.children.elementAt(1),expected.children.elementAt(1))

    }*/

    @Test
    fun `getRoot returns root`(){
        // arrange
        val expected = DataFixtures.getArtistsMenu()

        // act
        val actual = cut.findRoot()
        // Assert
        assertEquals("categories",actual)
    }

    @Test
    fun `getChildren returns children`(){
        // arrange
        val expected = arrayOf(
            "artists",
            "albums"
        )

        // act
        val actual = cut.getChildren("categories")
        // Assert
        assertArrayEquals(expected,actual)
    }
}