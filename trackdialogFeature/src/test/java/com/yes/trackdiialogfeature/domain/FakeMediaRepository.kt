package com.yes.trackdiialogfeature.domain

class FakeMediaRepository : IMediaRepository {
    /* fun getRootMenu(): Menu {
         val menu = Menu("Categories", null)
         val item1 = MediaItem("Media.ARTIST","artists")
         menu.children.add(item1)
         val item2 = MediaItem( "Media.GENRE","genres")
         menu.children.add(item2)
         val item3 = MediaItem("Media.TITLE", "tracks")
         menu.children.add(item3)

         return menu
     }


     override fun getMenu(menuParam: MenuParam): Menu {
        /* return if(menuParam.type==null
             && menuParam.what == null
             && menuParam.where == null){

             val menu = Menu("Categories", null)

             val item1 = MediaItem("Media.ARTIST","artists")
             menu.children.add(item1)
             val item2 = MediaItem("Media.GENRE","genres")
             menu.children.add(item2)
             val item3 = MediaItem("Media.TITLE","tracks")
             menu.children.add(item3)

             return menu
         } else if (menuParam.type.equals("Media.ARTIST")
             && menuParam.what == null
             && menuParam.where == null) {

             val item = MediaItem("Media.ARTIST","dire straits")
             val menu = Menu(menuParam.name, menuParam.parentMenu)
             menu.children.add(item)
             menu
         }else if(menuParam.type.equals("Media.TITLE")
             && menuParam.where.equals( "Media.ARTIST")
             && menuParam.what!!.get(0).equals("dire straits")){

             val item = MediaItem("Media.TITLE","money for nothing")
             val menu = Menu(menuParam.name, menuParam.parentMenu)
             menu.children.add(item)
             menu
         } else {
             Menu("", null)
         }*/
         return Menu("","")
     }*/

    fun getMedia(
        type: String,
        what: String,
        where: ArrayList<String>
    ): ArrayList<MediaItem> {
        TODO("Not yet implemented")
    }

    override fun getMedia(mediaQuery: MediaQuery): ArrayList<MediaItem> {
        val items = arrayListOf<MediaItem>()
        if (mediaQuery.type == "Media.ARTIST" &&
            mediaQuery.what == null &&
            mediaQuery.where == null
        ) {
            val item = MediaItem("Dire Straits")
            items.add(item)
        } else if (mediaQuery.type == "Media.TITLE" &&
            mediaQuery.what == "Media.ARTIST" &&
            mediaQuery.where == "Dire Straits"

        ) {
            val item = MediaItem("Money for Nothing")
            items.add(item)
        }


        return items
    }
}