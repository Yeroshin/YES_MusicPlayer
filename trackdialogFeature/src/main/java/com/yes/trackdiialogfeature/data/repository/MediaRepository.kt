package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.mapper.MediaMapper
import com.yes.trackdiialogfeature.data.mapper.MenuToParamMapper
import com.yes.trackdiialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdiialogfeature.domain.IMediaRepository
import com.yes.trackdiialogfeature.domain.Menu


class MediaRepository(private val mediaDataStore:MediaDataStore,private val menuMapper:MenuToParamMapper,private val mediaMapper:MediaMapper):IMediaRepository {
    override fun getMenu(oldMenu: Menu): Menu {
        val menu=Menu(oldMenu.items[oldMenu.selected!!].title!!,0,oldMenu, arrayListOf())
        menu.items=mediaMapper.mapToDomain(mediaDataStore.getMedia(menuMapper.map(oldMenu)))
        return menu
    }

}