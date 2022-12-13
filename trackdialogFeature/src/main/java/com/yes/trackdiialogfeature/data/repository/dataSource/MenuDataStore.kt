package com.yes.trackdiialogfeature.data.repository.dataSource

class MenuDataStore() {


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
    private val menuTree = mapOf(
        "categories" to null,
        /////////////////////////
        "artists" to "categories",
        "artistTracks" to "artists",
        ////////////////////////////
        "album" to "categories",
        "albumTracks" to "album",
        ////////////////////////////
        "genres" to "categories",
        "genreTracks" to "genres"
    )
    val data = mapOf(
        "artists" to "Media.ARTIST",
        "artistTracks" to "Media.TITLE",
        ////////////////////////////////
        "album" to "Media.ALBUM",
        "albumTracks" to "Media.TITLE",
        ////////////////////////////////
        "genres" to "Media.GENRE",
        "genreTracks" to "Media.TITLE"
    )

    fun getMenuTree(): Map<String, String?> {
        return menuTree
    }
    fun getMenuChildName(name:String): String {
        var menuChild:String=""
        for (item in menuTree) {
            if (item.value == name) {
                menuChild=item.key
                break
            }
        }
        return menuChild
    }
    fun getMenuType(menuName: String): String {
        return data[menuName]!!
    }
}