package com.yes.trackdiialogfeature.data.mapper

import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.IMenuRepository
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu

class FakeMenuRepository:IMenuRepository {
    override fun getRootMenu(): Menu {
        val menu=Menu("Categories",null)

        val item1=MediaItem()
        item1.type= MediaStore.Audio.Media.ARTIST
        item1.title="artists"
        menu.items?.add(item1)

        val item2=MediaItem()
        item2.type= MediaStore.Audio.Media.GENRE
        item2.title="genres"
        menu.items?.add(item2)

        val item3=MediaItem()
        item3.type= MediaStore.Audio.Media.TITLE
        item3.title="tracks"
        menu.items?.add(item3)

        return menu
    }

    override fun getMediaItems(menuParam: MenuParam): ArrayList<MediaItem> {
        TODO("Not yet implemented")
    }
}