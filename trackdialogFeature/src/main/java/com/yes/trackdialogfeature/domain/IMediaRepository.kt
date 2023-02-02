package com.yes.trackdialogfeature.domain


interface IMediaRepository {
   /* fun getMenu(): Menu
    fun getMenu(param: MenuParam): Menu*/
  // fun getMedia(mediaQuery: MediaQuery):ArrayList<MediaItem>
   fun getMediaItems(
      projection:Array<String>,
      selection:String?,
      selectionArgs:Array<String>?,
   ): ArrayList<String>
    //fun getMedia(type:String?,what:String?,where:ArrayList<String>):ArrayList<MediaItem>


}