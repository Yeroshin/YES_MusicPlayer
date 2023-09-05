package com.yes.trackdialogfeature.data.repository.dataSource

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import com.yes.trackdialogfeature.data.IMediaDataStore
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity


class MediaDataStore(private val context: Context) : IMediaDataStore {
   /* fun getMediaItems(
        projection: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
    ): List<MediaDataStoreEntity> {

        val audioList = arrayListOf<String>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

        // Show only videos that are at least 5 minutes in duration.
        /* val selection = "${MediaStore.Video.Media.DURATION} >= ?"
         val selectionArgs = arrayOf(
             TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString()
         )*/

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        /* val query = appContext.contentResolver.query(
             collection,
             projection,
             selection,
             selectionArgs,
             sortOrder
         )*/
        //MediaStore.Audio.Media.ARTIST,
        // MediaStore.Audio.Media.TITLE,
        /////////////////////////
        val projectio = arrayOf(
            // MediaStore.Audio.Genres.NAME,
            // MediaStore.Audio.Media.
            // MediaStore.Audio.Media.GENRE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
        )
        val selectio = null
        val selectionArg = emptyArray<String>();

        val query = context.contentResolver.query(
            collection,
            projectio,
            selectio,
            selectionArg,
            sortOrder
        )

        query?.use { cursor ->
            // Cache column indices.
            val column = cursor.getColumnIndex(projectio[1])
            while (cursor.moveToNext()) {
                audioList.add(cursor.getString(column))
            }
        }
        return audioList.map {
            MediaDataStoreEntity(
                it,
            )
        }
    }*/

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

        // Display videos in alphabetical order based on their display name.
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

    data class Param(
        val type: String,
        val selection: String?,
        val selectionArgs: Array<String>?
    )
}