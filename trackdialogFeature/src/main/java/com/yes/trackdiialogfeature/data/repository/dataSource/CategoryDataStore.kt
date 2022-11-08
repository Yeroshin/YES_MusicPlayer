package com.yes.trackdiialogfeature.data.repository.dataSource

import android.R
import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity

class CategoryDataStore(private val appContext: Activity) {
    fun getCategories(): MediaEntity {
        val media = MediaEntity()
        //tracks
        media.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.tracks)
        media.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            media.categoryName,
            MediaStore.Audio.Media.TITLE
        )
        //artists
        media.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.artists)
        media.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            media.categoryName,
            MediaStore.Audio.Media.ARTIST
        )
        //albums
        media.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.albums)
        media.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            media.categoryName,
            MediaStore.Audio.Media.ALBUM
        )
        //genres
        media.categoryName = appContext.getResources().getString(com.yes.coreui.R.string.genres)
        media.projection = appContext.getPreferences(Context.MODE_PRIVATE).getString(
            media.categoryName,
            MediaStore.Audio.Media.GENRE
        )
        return MediaEntity()
    }
}