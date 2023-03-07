package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.common.Result
class MenuRepository(
    private val menuDataStore: MenuDataStore,
    private val audioDataStore: AudioDataStore,
) {
    private lateinit var menu: Menu
    private val menuParent = menuDataStore.getMenuGraph()
    fun getMenu(): Result<Menu> {
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
                menu.type = item.key
                this.menu.children.add(menu)
            }
        }

        return Result.Success(menu)
    }

    fun getMenu(title:String, name:String): Result<Menu> {

      /*  var what: Array<String>?= null
        var where:String?= menuParent[name]?.let { menuDataStore.getMenuType(it) }
        val childMenuName = menuDataStore.getMenuChildName(name)
        val type =arrayOf( menuDataStore.getMenuType(name)!!)
        if(where!=null){
            where += "=?"
            what = arrayOf(title)
        }*/

        val childMenu = Menu(name)
        val childrenItems = audioDataStore.getMediaItems(
            arrayOf(),
            null,
            arrayOf()
        )
     /*   val childrenItems = audioDataStore.getMediaItems(
            type,
            where,
            what
        )
        for (item in childrenItems) {
            val itemMenu = Menu(childMenuName)
            itemMenu.type = item
            childMenu.children.add(itemMenu)
        }*/

        return Result.Success(childMenu)
    }
}