package com.yes.trackdiialogfeature.data.repository.dataSource

import android.R
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity

class CategoryDataStore(private val appContext: Activity) {
    fun getMenuNameForCategories(): String {
        return appContext.getPreferences(Context.MODE_PRIVATE).getString(
            "categoryName",
            appContext.getResources().getString(com.yes.coreui.R.string.categories)
        )!!
    }
    fun getCategories(): ArrayList<MediaEntity> {
        val items= arrayListOf<MediaEntity>()

        val mediaTracks = MediaEntity()
        //tracks
        mediaTracks .categoryName = appContext.getResources().getString(com.yes.coreui.R.string.tracks)
        mediaTracks .projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            mediaTracks .categoryName,
            MediaStore.Audio.Media.TITLE
        )
        items+=mediaTracks
        //artists
        val mediaAretists = MediaEntity()
        mediaAretists.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.artists)
        mediaAretists.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            mediaAretists.categoryName,
            MediaStore.Audio.Media.ARTIST
        )
        items+=mediaAretists
        //albums
        val mediaAlbums = MediaEntity()
        mediaAlbums.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.albums)
        mediaAlbums.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            mediaAlbums.categoryName,
            MediaStore.Audio.Media.ALBUM
        )
        items+=mediaAlbums
        //genres
        val mediaGenres = MediaEntity()
        mediaGenres.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.genres)
        mediaGenres.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            mediaGenres.categoryName,
            MediaStore.Audio.Media.GENRE
        )
        items+=mediaGenres
        return items
    }
}