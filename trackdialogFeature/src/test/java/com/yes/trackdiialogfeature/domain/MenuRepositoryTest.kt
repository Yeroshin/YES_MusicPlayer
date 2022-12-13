package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import kotlinx.coroutines.runBlocking

import org.junit.Test

class MenuRepositoryTest {

    @Test
    fun shouldReturnRootMenu() {
        val menuDataStore = MenuDataStore()
        val mediaRepository = FakeMediaRepository()
        val menuRepository = MenuRepository(menuDataStore,mediaRepository)
        //act
        val rootMenu = menuRepository.getMenu()
        //assert
        assert(false)

    }

    @Test
    fun shouldReturnChildMenu() {
     /*   val dataSource = MenuDataStore()
        val menuRepository = MenuRepository(dataSource)
        val rootMenu = menuRepository.getMenu()
        rootMenu.selected = 1
        val repository = MediaRepositoryTest()
        //act
        val menu = runBlocking {
            val media = ShowChildMenu(repository).run(rootMenu)
            val i=1
        }
        //assert
        assert(false)*/

    }
}