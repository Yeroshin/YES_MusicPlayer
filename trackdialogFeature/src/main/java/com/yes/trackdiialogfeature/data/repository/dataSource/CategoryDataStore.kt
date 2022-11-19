package com.yes.trackdiialogfeature.data.repository.dataSource

import android.R
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.domain.MediaItem

class CategoryDataStore(private val appContext: Activity) {
    fun getMenuNameForCategories(): String {
        return appContext.getPreferences(Context.MODE_PRIVATE).getString(
            "categoryName",
            appContext.getResources().getString(com.yes.coreui.R.string.categories)
        )!!
    }
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
    fun getCategories(): ArrayList<MediaEntity>{
        val items= arrayListOf<MediaEntity>()
        val itemsMap= mapOf(
            appContext.resources.getString(com.yes.coreui.R.string.tracks) to MediaStore.Audio.Media.TITLE,
            appContext.resources.getString(com.yes.coreui.R.string.artists) to MediaStore.Audio.Media.ARTIST,
            appContext.resources.getString(com.yes.coreui.R.string.albums) to MediaStore.Audio.Media.ALBUM,
            appContext.resources.getString(com.yes.coreui.R.string.genres) to MediaStore.Audio.Media.GENRE
        )
        for (keys in itemsMap){
            val item=MediaEntity()
            item.title=keys.key
            item.projection=keys.value
            items.add(item)
        }
        return items
    }
}