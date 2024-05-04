package com.yes.player.presentation.ui.tmp

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream


class YESAudioRecorder {
    private var rec: AudioRecord? = null
    private val sampleRate = 16000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private var isReading = false
    private val minInternalBufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        channelConfig,
        audioFormat
    ) * 10

    private var job: Job? = null
    private val byteArray = ByteArray(minInternalBufferSize)
    private var pipe:Array<ParcelFileDescriptor>?=null
    private var outputStream:ParcelFileDescriptor.AutoCloseOutputStream?=null
    @SuppressLint("MissingPermission")
    fun start() {
         pipe = ParcelFileDescriptor.createPipe()
        pipe?.get(1).let {
            outputStream = ParcelFileDescriptor.AutoCloseOutputStream(it)
        }


        rec = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            minInternalBufferSize
        )
        rec?.startRecording()
        isReading = true
        job = CoroutineScope(Dispatchers.IO).launch {
            while (isReading) {
                val bytesRead =rec?.read(byteArray, 0, byteArray.size) ?: 0
                if (bytesRead > 0) {
                    outputStream?.write(byteArray, 0, bytesRead)
                }
            }
            outputStream?.flush();
            outputStream?.close();
        }
    }

    fun stop(): ParcelFileDescriptor? {
        isReading = false
        job?.cancel()
        rec?.stop()
        rec?.release()
        rec = null
        return pipe?.get(0)
    }
}