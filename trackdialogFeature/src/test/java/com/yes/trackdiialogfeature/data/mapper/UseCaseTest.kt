package com.yes.trackdiialogfeature.data.mapper


import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu
import com.yes.trackdiialogfeature.domain.MenuInteractor
import com.yes.trackdiialogfeature.domain.showChildMenu
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
internal class UseCaseTest {

    var menu:Menu?=null

    @Test
    fun shouldReturnRootMenu() {
        val rep=FakeMenuRepository()
        menu= runBlocking {
            showChildMenu(rep).run(menu)
        }
        assertNotNull(menu)
        assertNull(menu?.parent)
    }
    @Test
    fun shouldReturnChildMenu() {
        val rep=FakeMenuRepository()
        val menu= runBlocking {
            showChildMenu(rep).run(menu)
        }
        assertNotNull(menu)
        assertEquals(this.menu,menu?.parent)
        assertEquals(3,menu.items?.size)
    }
}