package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu

class MenuRepository(private val repository: MenuDataStore) {
    private lateinit var rootMenu:Menu
    fun getMenu():Menu{
        val items=repository.getMenuTree()
        val menuTree= mutableMapOf<String,Menu>()
        //create all objects
        for (item in items){
            menuTree[item.key] = Menu(item.key,repository.getMenuType(item.key))
        }
        //set parent
        for (item in menuTree){
            val parent=menuTree[items[item.value.name]]
            if(parent==null){
                rootMenu=item.value
            }else{
                item.value.parent=parent
                parent.children.add(item.value)
                parent.items.add(MediaItem(item.value.name,"") )
            }

        }

        return rootMenu
    }
}