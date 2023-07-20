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
            .map {
                menuRepositoryMapper.mapToItem(it)
            }
        return menuEntity
            ?.let { it ->
                val menu = menuRepositoryMapper.mapToMenu(it)
                menu.children.toMutableList().addAll(items)
                return menu
            }
    }

    override fun getChildMenu(id: Int): Menu? {
        menuDataStore.getItemsWithParentId(id).lastOrNull()
            ?.let {
                return menuRepositoryMapper.mapToMenu(
                    it
                )
            } ?: return null
    }

    fun getChildItem(id: Int): Item? {
        val childId = menuDataStore.getItemsWithParentId(id).lastOrNull()
            ?.id
            ?:return null
        return menuRepositoryMapper.mapToItem(
            menuDataStore.getItemsWithParentId(childId).lastOrNull()?:return null
        )


    }
}