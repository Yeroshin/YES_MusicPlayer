package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
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
        return menuEntity?.let { it ->
                val menu = menuRepositoryMapper.mapToMenu(it)
                menu.copy(children = items)
            }
    }

    override fun getChildMenu(id: Int): Menu? {
        return menuDataStore.getItemsWithParentId(id).lastOrNull()?.let {
                menuRepositoryMapper.mapToMenu(it)
            }
    }

    override fun getChildItem(id: Int): Item? {
        return menuDataStore.getItemsWithParentId(id).lastOrNull()?.let {

                menuRepositoryMapper.mapToItem(it)

        }
    }
    override fun getItem(id:Int):Item?{
        return menuDataStore.getItem(id)?.let {
            menuRepositoryMapper.mapToItem(it)
        }

    }
}