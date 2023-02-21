package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MediaMapper
import com.yes.trackdialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.domain.IMediaDataStore


class MediaDataStore(
    private val audioDataStore: AudioDataStore,
    private val mediaMapper: MediaMapper,
    private val mediaQueryMapper: MediaQueryMapper
) : IMediaDataStore {

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

    override fun getMediaItems(
        projection: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
    ): ArrayList<String> {
        // val mediaQueryEntity=mediaQueryMapper.map(mediaQuery)
        // return mediaMapper.mapToDomain(audioDataStore.getMediaItems(mediaQueryEntity))
        //return arrayListOf(MediaItem("",""))
        return audioDataStore.getMediaItems(
            projection,
            selection,
            selectionArgs
        )
    }


}