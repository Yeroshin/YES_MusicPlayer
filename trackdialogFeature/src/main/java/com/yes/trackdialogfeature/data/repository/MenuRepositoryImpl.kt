package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.IMenuRepository
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepositoryImpl(
    private val menuMapper:MenuMapper,
    private val menuDataStore: MenuDataStore,
    private  val audioDataStore: AudioDataStore
):IMenuRepository {


    override fun getChildMenu(id: Int,name:String?): Menu {

        val menu=menuMapper.map(
            menuDataStore.getItemsWithParentId(id).last()
        )
        val items=audioDataStore.getMediaItems(

        ).map { menuMapper.mapToItem(it) }
        menu.children.toMutableList().addAll(items)
        return menu
    }

    override fun getRootItems(): List<Menu.Item> {
        TODO("Not yet implemented")
    }


}