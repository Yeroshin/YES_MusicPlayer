import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class TestMediaFileCreator(private val context: Context) {

    private val outputDir: File by lazy {
        getOutputDirectory()
    }

    private val createdFiles: ArrayList<String> = ArrayList()

    fun createTestMediaFilesFromAssets() {
        val assetManager = context.assets

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

    private fun getOutputDirectory(): File {
        // В данном примере файлы сохраняются в публичной директории "Movies"
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
    }

    private fun getFileAbsolutePath(fileName: String): String {
        return File(outputDir, fileName).absolutePath
    }
}