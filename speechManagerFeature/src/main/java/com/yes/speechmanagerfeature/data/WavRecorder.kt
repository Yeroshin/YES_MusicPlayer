package com.yes.speechmanagerfeature.data

import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class WavRecorder(
    private val sampleRate: Int,
    private val numChannels: Int,
    private val bitsPerSample: Int,
    fileName:String
) {
    private val mediaDir =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    private val outputFile = File(mediaDir, fileName)
    private val outputStream = FileOutputStream(outputFile)
    private var dataSize = 0
    init {
        if (!mediaDir.exists()) {
            mediaDir.mkdirs()
        }
        outputStream.write(getHeaderBytes(sampleRate, numChannels, bitsPerSample, dataSize))
    }
    fun write(output:ByteArray){
        outputStream.write(output, 0, output.size)
        dataSize += output.size
    }
    fun close(){
        outputStream.flush()
        updateHeader()
        outputStream.close()
    }
    private fun updateHeader() {
        outputStream.channel.position(0) // Перемещаемся в начало файла
        outputStream.write(getHeaderBytes(sampleRate, numChannels, bitsPerSample, dataSize)) // Пишем обновленный заголовок
    }
    private fun getHeaderBytes(
        sampleRate: Int,
        numChannels: Int,
        bitsPerSample: Int,
        dataSize: Int
    ): ByteArray {
        val headerSize = 44
        val header = ByteArray(headerSize)

        // ChunkID - "RIFF"
        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()

        // ChunkSize - общий размер файла минус 8 байтов
        val chunkSize = dataSize + 36
        header[4] = (chunkSize and 0xFF).toByte()
        header[5] = (chunkSize shr 8 and 0xFF).toByte()
        header[6] = (chunkSize shr 16 and 0xFF).toByte()
        header[7] = (chunkSize shr 24 and 0xFF).toByte()

        // Format - "WAVE"
        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()

        // Subchunk1ID - "fmt "
        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()

        // Subchunk1Size - 16 for PCM
        header[16] = 16
        header[17] = 0
        header[18] = 0
        header[19] = 0

        // AudioFormat - PCM = 1
        header[20] = 1
        header[21] = 0

        // NumChannels
        header[22] = numChannels.toByte()
        header[23] = 0

        // SampleRate
        header[24] = (sampleRate and 0xFF).toByte()
        header[25] = (sampleRate shr 8 and 0xFF).toByte()
        header[26] = (sampleRate shr 16 and 0xFF).toByte()
        header[27] = (sampleRate shr 24 and 0xFF).toByte()

        // ByteRate
        val byteRate = sampleRate * numChannels * bitsPerSample / 8
        header[28] = (byteRate and 0xFF).toByte()
        header[29] = (byteRate shr 8 and 0xFF).toByte()
        header[30] = (byteRate shr 16 and 0xFF).toByte()
        header[31] = (byteRate shr 24 and 0xFF).toByte()

        // BlockAlign
        val blockAlign = (numChannels * bitsPerSample / 8).toShort()
        header[32] = blockAlign.toByte()
        header[33] = 0

        // BitsPerSample
        header[34] = bitsPerSample.toByte()
        header[35] = 0

        // Subchunk2ID - "data"
        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()

        // Subchunk2Size - dataSize
        header[40] = (dataSize and 0xFF).toByte()
        header[41] = (dataSize shr 8 and 0xFF).toByte()
        header[42] = (dataSize shr 16 and 0xFF).toByte()
        header[43] = (dataSize shr 24 and 0xFF).toByte()

        return header
    }
}