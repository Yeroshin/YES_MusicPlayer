package com.yes.core.data.data.dataSource

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore

import com.yes.core.data.entity.MediaDataStoreEntity


class MediaDataStore(private val context: Context) {


    fun getAudioItems(
        where: String?,
        selectionArgs: Array<String>,
    ): List<MediaDataStoreEntity> {
        val selection = mapStringToConst(where)
        selectionArgs.forEach {  mapStringToConst(it) }
        val audioList = arrayListOf<MediaDataStoreEntity>()
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        /////////////////////////
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE
        )
        val select = selection?.let { "$it = ?" }// ?: "${MediaStore.Audio.Media.SIZE} = %"
        ///////////////////////
        val query = context.contentResolver.query(
            collection,
            projection,
            select,
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val titleColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
            val durationColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val dataColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            val sizeColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)
            while (cursor.moveToNext()) {
                audioList.add(
                    MediaDataStoreEntity(
                        cursor.getString(titleColumnIndex),
                        cursor.getString(artistColumnIndex),
                        cursor.getString(albumColumnIndex),
                        cursor.getLong(durationColumnIndex),
                        cursor.getString(dataColumnIndex),
                        cursor.getLong(sizeColumnIndex),
                    )
                )
            }
        }
        return audioList
    }

    private fun mapStringToConst(string: String?): String? {
        return when (string) {
            "artist" -> MediaStore.Audio.Media.ARTIST
            "album" -> MediaStore.Audio.Media.ALBUM
            "track" -> MediaStore.Audio.Media.TITLE
            else -> null
        }
    }

    fun getMediaItems(
        get: String,
        where: String?,
        what: Array<String>,
    ): List<MediaDataStoreEntity> {
        val type = mapStringToConst(get)
        val selection = mapStringToConst(where)
        what.forEach {  mapStringToConst(it) }

        val mediaList = mutableListOf<MediaDataStoreEntity>()
        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        /////////////////////////
        val projection = arrayOf(
            type
        )
        val select = selection?.let { "$it = ?" } //?: "${MediaStore.Audio.Media.SIZE} = %"
        ///////////////////////
        val query = context.contentResolver.query(
            collection,
            projection,
            select,
            what,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val columnIndex = cursor.getColumnIndex(type)
            while (cursor.moveToNext()) {

                when (cursor.getType(columnIndex)) {
                    Cursor.FIELD_TYPE_STRING -> mediaList.add(
                        MediaDataStoreEntity(
                            cursor.getString(columnIndex),
                        )
                    )

                    else -> {}
                }
            }
        }

        return mediaList
    }


}