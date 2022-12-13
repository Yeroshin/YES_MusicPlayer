package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.domain.IMediaRepository
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.common.BaseResult
import com.yes.trackdiialogfeature.domain.entity.Menu
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.util.concurrent.Flow

class MenuRepository(
    private val menuDataStore: MenuDataStore,
    private val mediaRepository: IMediaRepository,
) {
    private lateinit var menu: Menu
    private val menuTree = menuDataStore.getMenuTree()
    fun getMenu(): Menu {

        for (item in menuTree) {
            if (item.value == null) {
                menu = Menu(item.key, "")
                break
            }
        }
        for (item in menuTree) {
            if (item.value == menu.name) {
                val menu = Menu(
                    item.key,
                    menuDataStore.getMenuType(item.key)
                )
                menu.parent = this.menu
                menu.title = item.key
                this.menu.children.add(menu)
            }
        }
        return menu
    }

    fun getMenuChild(query: MediaQuery): BaseResult<Menu> {


        val menuName = menuDataStore.getMenuChildName(query.name)
        val menuType = menuDataStore.getMenuType(menuName)
        val menu = Menu(menuName, menuType)

        val childrenItems = mediaRepository.getMedia(query)
        for (item in childrenItems) {
            val itemMenu = Menu(menuName, menuType)
            itemMenu.parent = menu
            itemMenu.title = item.title
            menu.children.add(itemMenu)
        }

        return BaseResult.Success(menu)
    }
}