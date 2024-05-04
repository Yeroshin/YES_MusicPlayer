package com.yes.player.presentation.ui.tmp

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class YESAudioRecorder {
    private var rec: AudioRecord? = null
    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var isReading = false
    private var myBufferSize = 441000
    private val minInternalBufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        channelConfig,
        audioFormat
    ) * 10

    private var job: Job? = null
    @SuppressLint("MissingPermission")
    fun start(outputFile: File) {
        val outputStream = FileOutputStream(outputFile.absolutePath)
        val rec = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            minInternalBufferSize
        )
        rec.startRecording()
        isReading = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val data = ByteArray(minInternalBufferSize )
            while (isReading) {
                val readCount = rec.read(data, 0, data.size)
                outputStream.write(data, 0, readCount)
            }
            outputStream.flush()
            outputStream.close()
        }
    }

    fun stop() {
        isReading = false
        job?.cancel()
        rec?.stop()
        rec?.release()
        rec = null
    }


}