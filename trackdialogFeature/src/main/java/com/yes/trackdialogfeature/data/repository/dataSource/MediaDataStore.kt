package com.yes.trackdialogfeature.data.repository.dataSource

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.yes.trackdialogfeature.data.IMediaDataStore
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity


class MediaDataStore(private val appContext: Context) : IMediaDataStore {
    /*fun getMedia(
         projection: String?,
         selection: String?,
         selectionArgs: ArrayList<String>
     ): ArrayList<MediaEntity> {

        /* val par = MenuParam(
             MediaStore.Audio.Media.TITLE,
             MediaStore.Audio.Media.ARTIST + "=?",
             Array(1) { "The Soundtrack" })*/
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
             null,
             null,
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
                 media.type=MediaStore.Audio.Media.TITLE
                 media.uri = contentUri
                 media.title = title
                 media.artist = artist
                 // media.album=album
                 // media.genre=genre
                 media.duration = duration
                 media.size = size
                 audioList += media
             }
         }
         return audioList
     }*/
    fun getMediaItems(
        projection: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
    ): List<MediaDataStoreEntity> {

        /*  val mediaQue = MediaQueryEntity(
              Array(1){MediaStore.Audio.Media.TITLE},
              MediaStore.Audio.Media.ARTIST + "=?",
              Array(1) { "The Soundtrack" }
          )
         val mediaQuer = MediaQueryEntity(
             Array(1){"title"},
             MediaStore.Audio.Media.ARTIST + "=?",
             Array(1) { "Dire Straits" }
         )*/
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

        /*  val selectio = null
          val selectionArg =null*/
        ///////////////////////
        val query = appContext.contentResolver.query(
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
                "",
                "",
                0,
                "",
                0
            )
        }
    }

    fun getAudioItems(
        selection: String?,
        selectionArgs: Array<String>?,
    ): List<MediaDataStoreEntity> {
        val audioList = arrayListOf<MediaDataStoreEntity>()
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
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.SIZE
        )
        val select = selection?.let { "$it = ?" } ?: "${MediaStore.Audio.Media.SIZE} = %"
        ///////////////////////
        val query = appContext.contentResolver.query(
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
}