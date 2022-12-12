package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.domain.entity.MediaItem

interface IMediaRepository {
   /* fun getMenu(): Menu
    fun getMenu(param: MenuParam): Menu*/
   fun getMedia(mediaQuery: MediaQuery):ArrayList<MediaItem>
    //fun getMedia(type:String?,what:String?,where:ArrayList<String>):ArrayList<MediaItem>


}