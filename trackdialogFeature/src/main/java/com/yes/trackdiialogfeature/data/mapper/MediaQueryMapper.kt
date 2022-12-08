package com.yes.trackdiialogfeature.data.mapper

import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaQueryEntity
import com.yes.trackdiialogfeature.domain.MediaQuery

class MediaQueryMapper {
    fun map(mediaQuery: MediaQuery): MediaQueryEntity {
        val values = mapOf(
            "id" to MediaStore.Audio.Media._ID,
            "Media.TITLE" to MediaStore.Audio.Media.TITLE,
            "Media.ARTIST" to MediaStore.Audio.Media.ARTIST,
            "Media.ALBUM" to MediaStore.Audio.Media.ALBUM,
            "Media.GENRE" to MediaStore.Audio.Media.GENRE,
            "Media.DURATION" to MediaStore.Audio.Media.DURATION,
            "Media.SIZE" to MediaStore.Audio.Media.SIZE
        )
        var where:Array<String>?=null
        var what=values[mediaQuery.what]
        if(mediaQuery.what!=null){
            what="$what=?"
            where= mediaQuery.where?.let { arrayOf(it) }!!
        }

        return MediaQueryEntity(
            arrayOf(values[mediaQuery.type]),
            what,
            where
        )
    }
}