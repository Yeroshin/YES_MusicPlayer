package com.yes.trackdiialogfeature.data.repository.dataSource

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.yes.trackdiialogfeature.data.IMediaDataStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.data.repository.entity.MediaParam


class MediaDataStore(private val appContext: Context) : IMediaDataStore {
    fun getMedia(param: MediaParam): ArrayList<MediaEntity> {

        val par=MediaParam(MediaStore.Audio.Media.ARTIST+"=?",Array<String>(1){"The Soundtrack"})
        val audioList = ArrayList<MediaEntity>()

        val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
           // MediaStore.Audio.Media.GENRE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
        )

        // Show only videos that are at least 5 minutes in duration.
        /* val selection = "${MediaStore.Video.Media.DURATION} >= ?"
         val selectionArgs = arrayOf(
             TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString()
         )*/

        // Display videos in alphabetical order based on their display name.
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

        val query = appContext.contentResolver.query(
            collection,
            projection,
            par.where,
            par.what,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
           //val genreColumn = cursor.getColumnIndex(MediaStore.Audio.Media.GENRE)
            val durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)
               // val genre = cursor.getString(genreColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                val media = MediaEntity()
                media.uri = contentUri
                media.title = title
                media.artist=artist
                media.album=album
               // media.genre=genre
                media.duration=duration
                media.size=size
                audioList += media
            }
        }
        return audioList
    }
}