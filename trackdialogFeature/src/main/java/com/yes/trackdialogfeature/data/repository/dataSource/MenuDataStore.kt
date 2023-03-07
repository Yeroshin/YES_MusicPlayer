package com.yes.trackdialogfeature.data.repository.dataSource


import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel

class MenuDataStore {


    /*  fun getCategories(): ArrayList<MediaEntity> {
          val items= arrayListOf<MediaEntity>()

          val mediaTracks = MediaEntity()
          //tracks
          mediaTracks .title = appContext.getResources().getString(com.yes.coreui.R.string.tracks)
          mediaTracks .projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
              mediaTracks .title,
              MediaStore.Audio.Media.TITLE
          )
          items+=mediaTracks
          //artists
          val mediaAretists = MediaEntity()
          mediaAretists.title = appContext.getResources().getString(com.yes.coreui.R.string.artists)
          mediaAretists.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
              mediaAretists.title,
              MediaStore.Audio.Media.ARTIST
          )
          items+=mediaAretists
          //albums
          val mediaAlbums = MediaEntity()
          mediaAlbums.title = appContext.getResources().getString(com.yes.coreui.R.string.albums)
          mediaAlbums.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
              mediaAlbums.title,
              MediaStore.Audio.Media.ALBUM
          )
          items+=mediaAlbums
          //genres
          val mediaGenres = MediaEntity()
          mediaGenres.title = appContext.getResources().getString(com.yes.coreui.R.string.genres)
          mediaGenres.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
              mediaGenres.title,
              MediaStore.Audio.Media.GENRE
          )
          items+=mediaGenres
          return items
      }*/
    /*fun getItems():Map<String,String> {
        return  mapOf(
            appContext.resources.getString(com.yes.coreui.R.string.tracks) to MediaStore.Audio.Media.TITLE,
            appContext.resources.getString(com.yes.coreui.R.string.artists) to MediaStore.Audio.Media.ARTIST,
            appContext.resources.getString(com.yes.coreui.R.string.albums) to MediaStore.Audio.Media.ALBUM,
            appContext.resources.getString(com.yes.coreui.R.string.genres) to MediaStore.Audio.Media.GENRE
        )
    }
    fun getName():String {
        return  appContext.resources.getString(com.yes.coreui.R.string.categories)
    }*/
    private val menuGraph = mapOf(
        "categories" to null,
        /////////////////////////
        "artists" to "categories",
        "artistTracks" to "artists",
        ////////////////////////////
        "albums" to "categories",
        "albumTracks" to "album",
        ////////////////////////////
        /* "genres" to "categories",
         "genreTracks" to "genres"*/
    )
   /* val data = mapOf(
        "categories" to null,
        "artists" to MediaStore.Audio.Media.ARTIST,
        "artistTracks" to MediaStore.Audio.Media.TITLE,
        ////////////////////////////////
        "albums" to MediaStore.Audio.Media.ALBUM,
        "albumTracks" to MediaStore.Audio.Media.TITLE,
        ////////////////////////////////
        /* "genres" to "Media.GENRE",
         "genreTracks" to "Media.TITLE"*/
    )*/

    fun getMenuGraph(): Map<String, String?> {
        return menuGraph
    }

    fun getMenuChildName(name: String): String {
        var menuChild = ""
        for (item in menuGraph) {
            if (item.value == name) {
                menuChild = item.key!!
                break
            }
        }
        return menuChild
    }

  /*  fun getMenuType(menuName: String): String? {
        return data[menuName]
    }*/

    fun getRoot(): MenuApiModel {
        val type = menuGraph.filter { it.value == null }.keys.first()

        val children = menuGraph
            .filter { it.value == type }
            .keys
            .map { item -> MenuApiModel(item, item, arrayOf()) }
            .toTypedArray()

        return MenuApiModel(type, null, children)
    }

    //////////tmp
    private val menutree = mapOf(
        //name:"categories"
        "categories" to arrayOf(
            "tracks",
            "artists",
            "albums"
        ),
        /////////////////////////
        //name:"artists"
        "artists" to arrayOf(
            "artist"
        ),
        //name:"Dire Straits"
        "artist" to arrayOf(
            "artistTracks",
            "artistAlbums"
        ),
        //name:"Dire Straits"
        "artistTracks" to arrayOf(
            "track"
        ),
        //name:"artistAlbums"
        "artistAlbums" to arrayOf(
            "album"
        ),
        //name:"Brothers in Arms"
        "album" to arrayOf(
            "track"
        ),
        ////////////////////////////



        /* "genres" to "categories",
         "genreTracks" to "genres"*/
    )
fun getChild(name:String):String{
    return ""
}
    fun getChildren(name: String): Array<String> {
        return menutree.getValue(name)
    }

    fun findRoot(): String {
        var i = false
        var c=""

        menutree.forEach { menu->
            var k= true
            menutree.forEach { item ->
               var  f=item.value.filter {
                    c->c.equals(menu.key)
                }
                 if(!f.isEmpty()) {
                    k=false
                }
            }
            if (k) {
                if(!c.equals("")){
                    return "error!"
                }
                c = menu.key
            }

        }

        return c
    }



}