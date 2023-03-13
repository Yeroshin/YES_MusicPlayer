package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu
import java.util.*

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
    val stack =Stack<Menu>()
    fun getMenu(): Menu {
        val root = menuDataStore.getRoot()
        val children = menuDataStore.getChildren(0)
            .map {
                Menu.Item(
                    it.name ?: "",
                    it.id
                )
            }
        return Menu(
            root.name ?: "",
            children
        )

    }

    fun getMenu(id: Int, name: String): Menu {
        val children = menuDataStore.getChildren(id)
        val items = mutableListOf<Menu.Item>()
        children.forEach { item ->
            item.name?.let {
                items.add(
                    Menu.Item(
                        item.name,
                        item.id
                    ),
                )
            } ?: run {
                var selection=null
                if(!stack.isEmpty()){

                }
                audioDataStore.getMediaItems(
                    arrayOf(item.type!!),
                    selection,
                    emptyArray()
                )
                    .forEach { audioItem ->
                        items.add(
                            Menu.Item(
                                audioItem,
                                item.id
                            )
                        )
                    }

            }
        }
        stack.push(
            Menu(
                name,
                items
            )
        )
        return stack.peek()
    }
}