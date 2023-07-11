package com.yes.trackdialogfeature.data.repository.dataSource


import com.yes.trackdialogfeature.data.repository.entity.DataException
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntityOld

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

    /*  fun getRoot(): MenuApiModel {
          val type = menuGraph.filter { it.value == null }.keys.first()

          val children = menuGraph
              .filter { it.value == type }
              .keys
              .map { item -> MenuApiModel(item, item, arrayOf()) }
              .toTypedArray()

          return MenuApiModel(type, null, children)
      }*/

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
            "track"
        ),

        //name:"Brothers in Arms"
        "albums" to arrayOf(
            "track"
        ),
        ////////////////////////////


        /* "genres" to "categories",
         "genreTracks" to "genres"*/
    )

    fun getChild(name: String): String {
        return ""
    }

    /*  fun getChildren(name: String): Array<String> {
          return menutree.getValue(name)
      }*/

    /*  fun findRoot(): String {
          var i = false
          var c = ""

          menutree.forEach { menu ->
              var k = true
              menutree.forEach { item ->
                  var f = item.value.filter { c ->
                      c.equals(menu.key)
                  }
                  if (!f.isEmpty()) {
                      k = false
                  }
              }
              if (k) {
                  if (!c.equals("")) {
                      return "error!"
                  }
                  c = menu.key
              }

          }

          return c
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
    private val menuTree = listOf(
        mapOf(
            "id" to 0,
            "name" to "categories",
            "type" to null,
            "parent" to null
        ),
        mapOf(
            "id" to 1,
            "name" to "artists",
            "type" to null,
            "parent" to 0
        ),
        mapOf(
            "id" to 2,
            "name" to "albums",
            "type" to null,
            "parent" to 0
        ),
        mapOf(
            "id" to 3,
            "name" to "tracks",
            "type" to null,
            "parent" to 0
        ),
        mapOf(
            "id" to 4,
            "name" to null,
            "type" to "artist",
            "parent" to 1
        ),
        mapOf(
            "id" to 5,
            "name" to null,
            "type" to "album",
            "parent" to 2
        ),
        mapOf(
            "id" to 6,
            "name" to null,
            "type" to "track",
            "parent" to 3
        ),
        mapOf(
            "id" to 7,
            "name" to null,
            "type" to "track",
            "parent" to 4
        ),
        mapOf(
            "id" to 8,
            "name" to null,
            "type" to "track",
            "parent" to 5
        )


    )


    fun getItemOld(id: Int): MenuDataStoreEntityOld {
        /* var index=0
         for (ind in menuTree.indices) {
             if (menuTree[ind]["id"] == id) {
                 index=ind
             }
         }*/
        val foundItem = menuTree.find {
            it["id"] == id
        }!!


        return MenuDataStoreEntityOld(
            foundItem["id"] as Int,
            foundItem["name"] as String?,
            foundItem["type"] as String?,
            foundItem["parent"] as Int?
        )
    }

    fun getChildren(id: Int): Array<MenuDataStoreEntityOld> {
        val children = ArrayList<MenuDataStoreEntityOld>()
        for (item in menuTree) {
            if (item["parent"] == id) {
                children.add(
                    MenuDataStoreEntityOld(
                        item["id"] as Int,
                        item["name"] as String?,
                        item["type"] as String?,
                        item["parent"] as Int?
                    )
                )
            }
        }
        if (children.isEmpty()) {
            throw DataException
        }
        return children.toArray() as Array<MenuDataStoreEntityOld>
    }

    fun getRootOld(): MenuDataStoreEntityOld {
        var index = 0
        for (ind in menuTree.indices) {
            if (menuTree[ind]["parent"] == null) {
                index = ind
            }
        }

        return MenuDataStoreEntityOld(
            menuTree[index]["id"] as Int,
            menuTree[index]["name"] as String?,
            menuTree[index]["type"] as String?,
            menuTree[index]["parent"] as Int?
        )
    }

    fun getItemsWithParentId(value:Int): List<MenuDataStoreEntity> {
        val items = menuTree.filter {
            it["parent"] == value
        }.map {
            MenuDataStoreEntity(
                it["id"] as Int,
                it["name"] as String?,
                it["type"] as String?,
            )
        }

        return items
    }

    fun getItem(id: Int): MenuDataStoreEntity {

        val foundItem = menuTree.find {
            it["id"] == id
        }!!//?:throw  IllegalArgumentException("Элемент $elem не найден в списке!")

        @Suppress("UNCHECKED_CAST")
        return MenuDataStoreEntity(
            foundItem["id"] as Int,
            foundItem["name"] as String?,
            foundItem["type"] as String?
        )
    }
}


