package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.mapper.MediaMapper
import com.yes.trackdiialogfeature.data.repository.dataSource.CategoryDataStore
import com.yes.trackdiialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.IMenuRepository
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu


class MediaRepository(
    private val mediaDataStore: MediaDataStore,
    private val categoryDataStore: CategoryDataStore,
    private val mediaMapper: MediaMapper
) : IMenuRepository {
    private fun getRootMenu(): Menu {
        val menu = Menu(getRootName(), null)
        menu.items = getRootItems()
        return menu
    }

    private fun getRootName(): String {
        return categoryDataStore.getName()
    }

    private fun getRootItems(): ArrayList<MediaItem> {
        return mediaMapper.mapToDomain(categoryDataStore.getItems())
    }

    //////////////////////
    override fun getMenu(param: MenuParam): Menu {
        return if (param != null) {
            val childMenu = Menu(param.name, param.parentMenu)
            val items = mediaMapper.mapToDomain(mediaDataStore.getMedia(param.type, param.where, param.what))
            childMenu.items=items
            childMenu
        } else {
            getRootMenu()
        }
    }


}