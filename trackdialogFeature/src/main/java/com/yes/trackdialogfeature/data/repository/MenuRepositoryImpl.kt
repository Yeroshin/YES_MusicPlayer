package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepositoryImpl(
    private val menuDataStore: MenuDataStore,
    private val audioDataStore: AudioDataStore,
    private val menuMapper: MenuMapper
) {
/*
    fun getMenu(): MenuApiModel {
        return menuDataStore.getRoot()

    }

    fun getMenu(type: String, name: String?): MenuApiModel {
        val childsType = menuDataStore.getChild(type)
        val childsChildren = audioDataStore.getMediaItems(
            arrayOf(type),
            null,
            null
        )
            .map { item ->
                MenuApiModel(childsType, item, listOf())
            }

        return MenuApiModel(type, null, childsChildren)
    }*/

    fun getMenu(): MenuApiModel {
        val root= menuDataStore.findRoot()
        val children=menuDataStore.getChildren(root)
            .map {
                MenuApiModel(it,it, arrayOf())
            }.toTypedArray()
        return MenuApiModel(root,root, children )

    }

    fun getMenu(type: String, name: String): MenuApiModel {
        val childrenType = menuDataStore.getChildren(type)
        var selection:String?=null
        var args:Array<String>
        return if(childrenType.size==1){
             args  = type?.takeIf { it.equals(name)}?.let {
                 emptyArray()
            }?:run {
                 selection=type
                 arrayOf(name)

             }


            val children=audioDataStore.getMediaItems(childrenType,selection, args)
                .map {
                    MenuApiModel(childrenType[0],it, arrayOf())
                }.toTypedArray()
            MenuApiModel(type, name, children)
        }else{

            MenuApiModel(
                type,
                name,
                childrenType
                    .map { MenuApiModel(it, name, arrayOf()) }
                    .toTypedArray())
        }

    }
}