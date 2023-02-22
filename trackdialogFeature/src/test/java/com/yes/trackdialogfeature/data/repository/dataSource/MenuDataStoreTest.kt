package com.yes.trackdialogfeature.data.repository.dataSource

import com.yes.trackdialogfeature.data.DataFixtures
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class MenuDataStoreTest {
    private val cut=MenuDataStore()
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
    fun `getChild returns child MenuApiModel`(){
        // arrange
        val expected = DataFixtures.getArtistsMenu()

        // act
        val actual = cut.getChild(
            "artists"
        )
        // Assert
    }
}