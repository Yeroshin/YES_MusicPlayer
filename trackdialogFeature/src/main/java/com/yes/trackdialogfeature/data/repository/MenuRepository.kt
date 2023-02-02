package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.IMediaRepository
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepository(
    private val menuDataStore: MenuDataStore,
    private val mediaRepository: IMediaRepository,
) {
    private lateinit var menu: Menu
    private val menuParent = menuDataStore.getMenuTree()
    fun getMenu(): Menu {

        for (item in menuParent) {
            if (item.value == null) {
                menu = Menu(item.key)
                break
            }
        }
        for (item in menuParent) {
            if (item.value == menu.name) {
                val menu = Menu(
                    item.key
                )
                menu.parent = this.menu
                menu.title = item.key
                this.menu.children.add(menu)
            }
        }
        return menu
    }

    fun getMenu(title:String, name:String): Menu {


        var what: Array<String>?= null
        var where:String?= menuParent[name]?.let { menuDataStore.getMenuType(it) }
        val childMenuName = menuDataStore.getMenuChildName(name)
        val type =arrayOf( menuDataStore.getMenuType(name)!!)
        if(where!=null){
            where += "=?"
            what = arrayOf(title)
        }

        val childMenu = Menu(name)

        val childrenItems = mediaRepository.getMediaItems(
            type,
            where,
            what
        )
        for (item in childrenItems) {
            val itemMenu = Menu(childMenuName)
            itemMenu.title = item
            childMenu.children.add(itemMenu)
        }

        return childMenu
    }
}