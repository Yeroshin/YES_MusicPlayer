package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaQueryEntity
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.Menu

class MenuRepository(private val menuDataStore: MenuDataStore) {
    private lateinit var rootMenu: Menu
    private val menuTree=menuDataStore.getMenuTree()
    fun getMenu(): Menu {

        for (item in menuTree){
            if(item.value==null){
                rootMenu=Menu(item.key)
                break
            }
        }
        for(item in menuTree){
            if(item.value==rootMenu.name){
                val menu=Menu(item.key)
                menu.parent=rootMenu
                menu.title=item.key
                menu.type=menuDataStore.getMenuType(item.key)
                rootMenu.children.add(menu)
            }
        }


        return rootMenu
    }
    fun getMenuChild(name:String):Menu{
        for (item in menuTree){
            if(item.value==name){
                val menu= Menu(item.key)
                menu.type=menuDataStore.getMenuType(item.key)
                return menu
            }
        }
        return Menu("")
    }
}