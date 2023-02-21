package com.yes.trackdialogfeature.data.repository.dataSource

import com.yes.trackdialogfeature.data.DataFixtures
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class MenuDataStoreTest {
    private val cut=MenuDataStore()
    @Test
    fun `getRoot returns root MenuApiModel`() {

        // arrange
        val expected = DataFixtures.getRootMenuApiModel()

        // act
        val actual = cut.getRoot()
        // Assert
        assertEquals(actual.name,expected.name)
        assertEquals(actual.type,expected.type)
     //   assertEquals(actual.children,expected.children)
    }

    @Test
    fun `getChild returns child MenuApiModel`(){
        // arrange
        val expected = DataFixtures.getChildMenu()

        // act
        val actual = cut.getChild(
            "artists"
        )
        // Assert
    }
}