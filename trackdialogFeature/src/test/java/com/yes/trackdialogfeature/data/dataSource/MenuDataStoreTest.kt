package com.yes.trackdialogfeature.data.dataSource


import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import org.junit.jupiter.api.Test

internal class MenuDataStoreTest {
    private val cut = MenuDataStore()

    @Test
    fun `returns correct Item with specified id`(){
        // arrange
        val expected = MenuDataStoreFixtures.getCategoriesMenu()
        // act
        val actual = cut.getItem(
            expected.id
        )
        // Assert
        assert(actual==expected)
    }

    @Test
    fun `returns root items`(){
        val expected = MenuDataStoreFixtures.getCategoriesMenuItems()
        // act
        val actual = cut.getItemsWithParentId(0)
        // Assert
        assert(actual==expected)
    }



}