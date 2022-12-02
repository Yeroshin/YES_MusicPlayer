package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.data.repository.entity.MenuParam

interface IMenuRepository {
   /* fun getMenu(): Menu
    fun getMenu(param: MenuParam): Menu*/
   fun getMedia(mediaQuery: MediaQuery):ArrayList<MediaItem>
    //fun getMedia(type:String?,what:String?,where:ArrayList<String>):ArrayList<MediaItem>


}