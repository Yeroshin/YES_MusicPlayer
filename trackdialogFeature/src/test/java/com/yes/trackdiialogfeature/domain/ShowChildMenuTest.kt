package com.yes.trackdiialogfeature.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
internal class ShowChildMenuTest {


    private lateinit var repository: FakeMenuRepository

    @Before
    fun setUp() {
        repository = FakeMenuRepository()
    }

    @Test
    fun `With Menu = null should Return Root Menu`() {
        //arrange
        val parentMenu: Menu? = null
        //act
        val childMenu = runBlocking {
            ShowChildMenu(repository).run(parentMenu)
        }
        //assert
        assertNotNull(childMenu)
        assertNull(childMenu.parent)
    }

    @Test
    fun `Root Menu should Return category Menu from selected item`() {
        //arrange
        val categoriesMenu = Menu("categories", null)
        categoriesMenu.selected = 1
        val item1 = MediaItem("Media.ARTIST", "genres")
        val item2 = MediaItem("Media.ARTIST", "artists")
        categoriesMenu.items.add(item1)
        categoriesMenu.items.add(item2)
        ///
        val expectedMenu = Menu("artists", categoriesMenu)
        val item3 = MediaItem("Media.ARTIST", "dire straits")
        expectedMenu.items.add(item3)
        //act
        val artistsMenu = runBlocking {
            ShowChildMenu(repository).run(categoriesMenu)
        }
        //assert
        assertNotNull(artistsMenu)
        assert(artistsMenu.parent!!.name.equals(categoriesMenu.name))
        assertEquals(1, artistsMenu.items.size)

        assert(artistsMenu.name.equals(expectedMenu.name))
        assert(artistsMenu.items[0].type.equals(item3.type))
        assert(artistsMenu.items[0].title.equals(item3.title))
    }

    @Test
    fun `category Menu should return track Menu from selected item `(){
        //arrange
        val categoriesMenu = Menu("Categories", null)
        categoriesMenu.selected=0
        val item1 = MediaItem("Media.ARTIST","artists")
        categoriesMenu.items.add(item1)

        val artistsMenu = Menu("artists", categoriesMenu )
        artistsMenu.selected=0
        val item3 = MediaItem("Media.TITLE", "dire straits")
        artistsMenu.items.add(item3)


        val expectedMenu = Menu("tracks", artistsMenu)
        val item = MediaItem("Media.TITLE","money for nothing")
        expectedMenu.items.add(item)
        //act
        val trackMenu = runBlocking {
            ShowChildMenu(repository).run(artistsMenu)
        }
        //assert
      /*  assertEquals(1,trackMenu.items.size)
        assert(trackMenu.items[0].type.equals(expectedMenu.items[0].type))
        assert(trackMenu.name.equals(expectedMenu.name))*/

    }
}