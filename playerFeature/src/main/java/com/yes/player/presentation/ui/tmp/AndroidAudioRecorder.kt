package com.yes.player.presentation.ui.tmp

import android.content.ContentValues.TAG
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.media3.extractor.OpusUtil.SAMPLE_RATE
import java.io.File
import java.io.FileOutputStream


class AndroidAudioRecorder(
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var rec: AudioRecord? = null
    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var isReading = false
    private var myBufferSize = 441000
    private val BUFFER_SIZE_RECORDING =
        AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    init {
        rec = createRecorder()
    }

    private fun createRecorder(): AudioRecord {

        /*  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
              MediaRecorder(context)
          } else MediaRecorder()*/
        val minInternalBufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            channelConfig, audioFormat
        )
        val internalBufferSize = minInternalBufferSize * 4
        return AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate, channelConfig, audioFormat, internalBufferSize
        )
    }
    private var newFile:File?=null
    private var outputStream:FileOutputStream? = null
    override fun start(outputFile: File) {
        newFile = outputFile
        outputStream = FileOutputStream(outputFile.absolutePath)


        rec?.startRecording()
        readStart()

        /*  createRecorder().apply {
              setAudioSource(MediaRecorder.AudioSource.MIC)
              setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
              setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
              setOutputFile(FileOutputStream(outputFile).fd)

              prepare()
              start()

              recorder = this*/
    }


    override fun stop() {
        readStop()
        rec?.stop()
        rec?.release()
        rec=null
        /*recorder?.stop()
        recorder?.reset()
        recorder = null*/
    }

    private fun readStart() {

        isReading = true
        Thread {
            rec?.let { rec ->
                val data = ByteArray(BUFFER_SIZE_RECORDING / 2)
                var readCount = 0
                var totalCount = 0
                while (isReading) {
                    readCount = rec.read(data, 0, data.size)
                    totalCount += data.size

                    outputStream?.write(data, 0, readCount)
                        //writeBufferToFile(myBuffer, it)


                }
                outputStream?.flush()
                outputStream?.close()
            }
        }.start()
    }

    private fun readStop() {
        Log.d(TAG, "read stop")
        isReading = false

    }
    private fun writeBufferToFile(buffer: ByteArray, outputFile: File) {
        try {
            val fileOutputStream = FileOutputStream(outputFile, true) // true для дозаписи в конец файла
            fileOutputStream.write(buffer)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}