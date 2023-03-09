package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

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

    fun getMenu(): MenuDataStoreEntity {
      /*  val root= menuDataStore.findRoot()
        val children=menuDataStore.getChildren(root)
            .map {
                MenuDataStoreEntity(it,it, arrayOf())
            }.toTypedArray()
        return MenuDataStoreEntity(root,root, children )*/
        TODO("Not yet implemented")
    }

    fun getMenu(type: String, name: String): MenuDataStoreEntity {

       /* val childrenType = menuDataStore.getChildren(type)
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
                    MenuDataStoreEntity(childrenType[0],it, arrayOf())
                }.toTypedArray()
            MenuDataStoreEntity(type, name, children)
        }else{

            MenuDataStoreEntity(
                type,
                name,
                childrenType
                    .map { MenuDataStoreEntity(it, name, arrayOf()) }
                    .toTypedArray())
        }*/
        TODO("Not yet implemented")
    }
}