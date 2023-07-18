package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.IMenuRepository
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

class MenuRepositoryImpl(
    private val menuRepositoryMapper: MenuRepositoryMapper,
    private val menuDataStore: MenuDataStore,
) : IMenuRepository {
    override fun getRootMenu(): Menu? {
        val rootMenuId = menuDataStore.getRootMenuId()
        val menuEntity = menuDataStore.getItem(rootMenuId)
        val items = menuDataStore.getItemsWithParentId(rootMenuId)
            .mapNotNull {
                menuRepositoryMapper.mapToItem(it)
            }
        return menuEntity
            ?.let { it ->
                menuRepositoryMapper.mapToMenu(it)
                    ?.let { menu ->
                        menu.children.toMutableList().addAll(items)
                        return menu
                    }
                    ?: run { return null }
            }
    }
    override fun getChildMenu(id: Int): Menu? {
        return menuRepositoryMapper.mapToMenu(
            menuDataStore.getItemsWithParentId(id).last()
        )
    }
    fun getChildItem(id: Int): Item? {
        val childId=menuDataStore.getItemsWithParentId(id).last().id
        return menuRepositoryMapper.mapToItem(
            menuDataStore.getItemsWithParentId(childId).last()
        )


    }
}