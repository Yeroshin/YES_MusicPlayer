package com.example.shared_test

import android.content.Context
import android.database.Cursor
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MediaFileFixtures(private val context: Context) {

    private val selectedArtist = UiFixtures.getSelectedArtistIndex()
    private val selectedArtistSelectedTrack = UiFixtures.getSelectedArtistSelectedTrack()
    private val outputDir: File by lazy {
        getOutputDirectory()
    }

    private val createdFiles: ArrayList<String> = ArrayList()
    private val assetManager = context.assets
    fun createTestMediaFilesFromAssets() {
        // val assetManager = context.assets

        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        try {
            val assetFiles = assetManager.list("media")
            if (assetFiles.isNullOrEmpty()) {
                return
            }

            for (assetFile in assetFiles) {
                val outputFile = File(outputDir, assetFile)
                val inputStream = assetManager.open("media/$assetFile")
                val outputStream = FileOutputStream(outputFile)

                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                // Обновляем файлы в MediaStore
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(outputFile.absolutePath),
                    null,
                    null
                )

                createdFiles.add(assetFile)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun deleteTestMediaFiles() {
        if (outputDir.exists()) {
            val mediaFiles = outputDir.listFiles()
            if (!mediaFiles.isNullOrEmpty()) {
                val deletedFiles = ArrayList<String>()
                for (mediaFile in mediaFiles) {
                    val fileName = mediaFile.name
                    if (createdFiles.contains(fileName)) {
                        mediaFile.delete()
                        deletedFiles.add(fileName)
                    }
                }

                // Обновляем файлы в MediaStore после удаления
                MediaScannerConnection.scanFile(
                    context,
                    deletedFiles.map { getFileAbsolutePath(it) }.toTypedArray(),
                    null,
                    null
                )

                createdFiles.removeAll(deletedFiles)
            }
        }
    }

    private fun writeFileFromAsset(outputDir: File, fileName: String) {
        try {
            val outputFile = File(outputDir, fileName)
            val inputStream = assetManager.open("media/$fileName")
            val outputStream = FileOutputStream(outputFile)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()


            // Обновляем файлы в MediaStore
            MediaScannerConnection.scanFile(
                context,
                arrayOf(outputFile.absolutePath),
                null,
                null
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeNonExistFiles() {
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val assetFiles = assetManager.list("media")
        if (assetFiles.isNullOrEmpty()) {
            return
        }

        for (assetFile in assetFiles) {
            if (!checkFileExist(assetFile)) {
                writeFileFromAsset(outputDir, assetFile)
            }
        }
    }

    private fun getOutputDirectory(): File {
        // В данном примере файлы сохраняются в публичной директории "Movies"
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    }

    private fun getFileAbsolutePath(fileName: String): String {
        return File(outputDir, fileName).absolutePath
    }

    private fun getMedia(
        type: String,
        selection: String?,
        selectionArgs: Array<String>?
    ): List<Any?> {

        val audioList = mutableListOf<Any?>()
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
            selectionArgs,
            sortOrder
        )
        query?.use { cursor ->
            // Cache column indices.
            val columnIndex = cursor.getColumnIndex(type)
            while (cursor.moveToNext()) {

                when (cursor.getType(columnIndex)) {
                    Cursor.FIELD_TYPE_NULL -> audioList.add(
                        null
                    )

                    Cursor.FIELD_TYPE_INTEGER -> audioList.add(
                        cursor.getLong(columnIndex)
                    )

                    Cursor.FIELD_TYPE_FLOAT -> audioList.add(
                        cursor.getDouble(columnIndex)
                    )

                    Cursor.FIELD_TYPE_STRING -> audioList.add(
                        cursor.getString(columnIndex),
                    )

                    Cursor.FIELD_TYPE_BLOB -> audioList.add(
                        cursor.getBlob(columnIndex)
                    )

                    else -> audioList.add(null)
                }
            }
        }

        return audioList
    }


    private fun checkFileExist(fileName: String): Boolean {
        val displayName = getMedia(
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DISPLAY_NAME,
            arrayOf(fileName)
        ) as List<*>
        return displayName.isNotEmpty()
    }

    fun getFileNames(): List<String> {
        return getMedia(
            MediaStore.Audio.Media.DISPLAY_NAME,
            null,
            null
        ) as List<String>
    }

    private val onClick: (TrackDialogContract.Event) -> Unit = {}

    fun getArtists(): List<MenuUi.ItemUi> {
        val artists = getMedia(
            MediaStore.Audio.Media.ARTIST,
            null,
            null
        ) as List<String>
        return artists.map {
            MenuUi.ItemUi(
                7,
                it,
                1,
                false,
                TrackDialogContract.Event.OnItemClicked(
                    7,
                    it,
                ),
                onClick
            )
        }
    }

    fun getSelectedArtistTracks(): List<MenuUi.ItemUi> {
        val artist = getArtists()[selectedArtist].name
        val tracks = getMedia(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            arrayOf(artist)
        ) as List<String>
        return tracks.map {
            MenuUi.ItemUi(
                7,
                it,
                1,
                false,
                TrackDialogContract.Event.OnItemClicked(
                    7,
                    it,
                ),
                onClick
            )
        }

    }

    fun getSelectedArtistSelectedTrack(): List<MenuUi.ItemUi> {
        return listOf(getSelectedArtistTracks()[selectedArtistSelectedTrack])
    }




}