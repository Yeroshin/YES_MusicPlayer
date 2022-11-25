package com.yes.trackdiialogfeature.domain

import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.IMenuRepository
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu

class FakeMenuRepository : IMenuRepository {
    fun getRootMenu(): Menu {
        val menu = Menu("Categories", null)
        val item1 = MediaItem("Media.ARTIST","artists")
        menu.items.add(item1)
        val item2 = MediaItem( "Media.GENRE","genres")
        menu.items.add(item2)
        val item3 = MediaItem("Media.TITLE", "tracks")
        menu.items.add(item3)

        return menu
    }


    override fun getMenu(menuParam: MenuParam): Menu {
        return if(menuParam.type==null
            && menuParam.what == null
            && menuParam.where == null){

            val menu = Menu("Categories", null)

            val item1 = MediaItem("Media.ARTIST","artists")
            menu.items.add(item1)
            val item2 = MediaItem("Media.GENRE","genres")
            menu.items.add(item2)
            val item3 = MediaItem("Media.TITLE","tracks")
            menu.items.add(item3)

            return menu
        } else if (menuParam.type.equals("Media.ARTIST")
            && menuParam.what == null
            && menuParam.where == null) {

            val item = MediaItem("Media.ARTIST","dire straits")
            val menu = Menu(menuParam.name, menuParam.parentMenu)
            menu.items.add(item)
            menu
        }else if(menuParam.type.equals("Media.TITLE")
            && menuParam.where.equals( "Media.ARTIST")
            && menuParam.what!!.get(0).equals("dire straits")){

            val item = MediaItem("Media.TITLE","money for nothing")
            val menu = Menu(menuParam.name, menuParam.parentMenu)
            menu.items.add(item)
            menu
        } else {
            Menu("", null)
        }
    }
}