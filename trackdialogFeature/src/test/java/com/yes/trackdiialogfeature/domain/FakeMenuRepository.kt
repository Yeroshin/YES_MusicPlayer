package com.yes.trackdiialogfeature.domain

import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.IMenuRepository
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu

class FakeMenuRepository : IMenuRepository {
    fun getRootMenu(): Menu {
        val menu = Menu("Categories", null)

        val item1 = MediaItem()
        item1.type = MediaStore.Audio.Media.ARTIST
        item1.title = "artists"
        menu.items?.add(item1)

        val item2 = MediaItem()
        item2.type = MediaStore.Audio.Media.GENRE
        item2.title = "genres"
        menu.items?.add(item2)

        val item3 = MediaItem()
        item3.type = MediaStore.Audio.Media.TITLE
        item3.title = "tracks"
        menu.items?.add(item3)

        return menu
    }


    override fun getMenu(menuParam: MenuParam): Menu {
        return if(menuParam.type==null
            && menuParam.what == null
            && menuParam.where == null){
            val menu = Menu("Categories", null)

            val item1 = MediaItem()
            item1.type ="Media.ARTIST"
            item1.title = "artists"
            menu.items?.add(item1)

            val item2 = MediaItem()
            item2.type = "Media.GENRE"
            item2.title = "genres"
            menu.items?.add(item2)

            val item3 = MediaItem()
            item3.type = "Media.TITLE"
            item3.title = "tracks"
            menu.items?.add(item3)

            return menu
        } else if (menuParam.type.equals("Media.ARTIST")
            && menuParam.what == null
            && menuParam.where == null) {
            val item = MediaItem()
            item.title = "dire straits"
            item.type = "Media.TITLE"
            val menu = Menu(menuParam.name, menuParam.parentMenu)
            menu
        }else if(menuParam.type.equals("Media.ARTIST")
            && menuParam.what .equals( "Media.TITLE")
            && menuParam.where.equals("dire straits")){
            val item = MediaItem()
            item.title = "money for nothing"
            item.type = "Media.TITLE"
            val menu = Menu(menuParam.name, menuParam.parentMenu)
            menu
        } else {
            Menu("", null)
        }
    }
}