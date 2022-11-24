package com.yes.trackdiialogfeature.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
internal class ShowChildMenuTest {


    private  lateinit var repository: FakeMenuRepository
    @Before
    fun setUp(){
         repository= FakeMenuRepository()
    }

    @Test
    fun `With Menu = null should Return Root Menu`() {
        //arrange
        val parentMenu:Menu?=null
        //act
        val childMenu= runBlocking {
            ShowChildMenu(repository).run(parentMenu)
        }
        //assert
        assertNotNull(childMenu)
        assertNull(childMenu?.parent)
    }
    @Test
    fun `Root Menu should Return category Menu from selected item`() {
        //arrange
        val parentMenu =Menu("categories",null)
        parentMenu.selected=1
        val item1=MediaItem()
        item1.type="Media.ARTIST"
        item1.title="artists"
        val item2=MediaItem()
        item2.type="Media.ARTIST"
        item2.title="genres"
        parentMenu.items[0]= MediaItem()
        ///
        val expectedMenu =Menu("categories",null)
        parentMenu.selected=1
        val item3=MediaItem()
        item3.type="Media.ARTIST"
        item3.title="dire straits"
        expectedMenu.items[1]= MediaItem()
        //act
        val childMenu= runBlocking {
            ShowChildMenu(repository).run(parentMenu)
        }
        //assert
        assertNotNull(childMenu)
        assertEquals(childMenu,parentMenu?.parent)
        assertEquals(3,childMenu.items?.size)

        assert(childMenu.name.equals(expectedMenu.name))
        assert(childMenu.items[0].type.equals(item3.type))
        assert(childMenu.items[0].title.equals(item3.title))
    }
}