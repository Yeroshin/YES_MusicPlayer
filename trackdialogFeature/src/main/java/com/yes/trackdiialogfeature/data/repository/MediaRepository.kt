package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.mapper.MediaMapper
import com.yes.trackdiialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.IMenuRepository
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.Menu


class MediaRepository(
    private val audioDataStore: AudioDataStore,
    private val mediaMapper: MediaMapper,
    private val mediaQueryMapper: MediaQueryMapper
) : IMenuRepository {

   /* private fun getRootName(): String {
        return menuDataStore.getName()

    }*/

   /* private fun getRootItems(): ArrayList<MediaItem> {
        return mediaMapper.mapToDomain(menuDataStore.getItems())
    }*/

  /*  override fun getMenu(): Menu {
       /* val menu = Menu(getRootName(), null)
        menu.items = getRootItems()
        return menu*/
        return Menu("","")
    }

    //////////////////////
    override fun getMenu(param: MenuParam): Menu {

           /* val childMenu = Menu(param.name, param.parentMenu)
            val items = mediaMapper.mapToDomain(audioDataStore.getMedia(param.type, param.where!!, param.what!!))
            childMenu.items=items
            return childMenu*/
        return Menu("","")

    }*/

    override fun getMedia(mediaQuery: MediaQuery):ArrayList<MediaItem>{
        val mediaQueryEntity=mediaQueryMapper.map(mediaQuery)
         return mediaMapper.mapToDomain(audioDataStore.getMedia(mediaQueryEntity))
        //return arrayListOf(MediaItem("",""))
    }


}