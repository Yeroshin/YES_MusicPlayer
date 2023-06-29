package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.IMenuRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepositoryImpl(
    private val menuMapper: MenuMapper,
    private val menuDataStore: MenuDataStore,
    private val audioDataStore: AudioDataStore
) : IMenuRepository {


    override fun getChildMenu(id: Int, name: String): DomainResult<Menu> {



        val currentMenu=menuDataStore.getItem(id)
        val childMenu = menuDataStore.getItemsWithParentId(id).last()


        val selection: String? = currentMenu.type
        val selectionArgs = selection?.let {
            arrayOf(name)
        } ?: run {
            emptyArray()
        }
        val projection=childMenu.type?.let { arrayOf(it) }?: arrayOf()
        val items = audioDataStore.getMediaItems(
            projection,
            selection,
            selectionArgs,
        ).map { menuMapper.mapToItem(it) }
        val menu = menuMapper.map(childMenu)
        menu.children.toMutableList().addAll(items)
        return DomainResult.Success(menu)
    }

    override fun getRootItems(): DomainResult<List<Menu.Item>> {
        TODO("Not yet implemented")
    }


}