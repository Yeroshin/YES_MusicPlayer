package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.domain.IMediaRepository
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.common.BaseResult
import com.yes.trackdiialogfeature.domain.entity.Menu

class MenuRepository(
    private val menuDataStore: MenuDataStore,
    private val mediaRepository: IMediaRepository,
) {
    private lateinit var menu: Menu
    private val menuParent = menuDataStore.getMenuTree()
    fun getMenu(): BaseResult<Any?> {

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
        return BaseResult.Success(menu)
    }

    fun getMenu(query: MediaQuery): BaseResult<Any?> {


        var what: Array<String>?= null
        var where:String?= menuParent[query.name]?.let { menuDataStore.getMenuType(it) }
        val childMenuName = menuDataStore.getMenuChildName(query.name)
        val type =arrayOf( menuDataStore.getMenuType(query.name)!!)
        if(where!=null){
            where += "=?"
            what = arrayOf(query.title!!)
        }

        val childMenu = Menu(query.name)

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

        return BaseResult.Success(childMenu)
    }
}