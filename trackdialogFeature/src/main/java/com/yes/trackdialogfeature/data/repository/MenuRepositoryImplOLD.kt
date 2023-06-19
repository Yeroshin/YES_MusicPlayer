package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.DataException
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.repository.MenuRepository
import com.yes.trackdialogfeature.domain.repository.MenuRepository.Params

class MenuRepositoryImplOLD(
    private val menuDataStore: MenuDataStore,
    private val audioDataStore: AudioDataStore
):MenuRepository {
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

    override fun getMenu(): DomainResult<Menu> {
        val root = menuDataStore.getRootOld()
        val children = menuDataStore.getChildren(0)
            .map {
                Menu.Item(
                    it.name!!,
                    it.id
                )
            }
        return DomainResult.Success(
            Menu(
                root.name!!,
                children
            )
        )

    }

    override fun getMenu(id: Int, name: String): DomainResult<Menu> {
        val children = try {
            menuDataStore.getChildren(id)
        }catch (e:DataException){
            return DomainResult.Error(MenuException.Empty)
        }
        val items = mutableListOf<Menu.Item>()


        children.forEach { item ->
            item.type?.let {
                val parent = menuDataStore.getItemOld(id)
                val selection: String? = parent.type
                val arg = selection?.let {
                    arrayOf(name)
                } ?: run {
                    emptyArray()
                }
                audioDataStore.getMediaItems(
                    arrayOf(item.type),
                    selection,
                    arg
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

        return DomainResult.Success(
            Menu(
                name,
                items
            )
        )
    }

    override fun getMenu(params: Params){

    }
}