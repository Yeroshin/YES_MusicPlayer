package com.yes.trackdialogfeature.data.dataSource


import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore


import org.junit.Test

internal class MenuDataStoreTest {
    private val cut = MenuDataStore()
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

    /*  @Test
      fun `getRoot returns root`(){
          // arrange
          val expected = DataSourceFixtures.getArtistsMenu()

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
      }*/

    ////////////////////////////
    ////////////////////////////
    @Test
    fun `getRoot returns root MenuApiModel`() {

        // arrange
        val expected = MenuDataStoreFixtures.getRoot()
        // act
        val actual = cut.getRoot()
        // Assert
        assert(actual == expected)

    }

    @Test
    fun `getChildren returns children MenuApiModels`() {

        // arrange
        val expected = MenuDataStoreFixtures.getCategoriesChildren()
        // act
        val actual = cut.getChildren(0)
        // Assert
        assert(actual.contentEquals(expected))

    }
    @Test
    fun `getItem with id 3 returns correct Item`(){
        // arrange
        val expected = MenuDataStoreFixtures.getAlbums()
        // act
        val actual = cut.getItem(
            3
        )
        // Assert
        assert(actual==expected)
    }

}