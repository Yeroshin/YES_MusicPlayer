package com.yes.player.presentation.ui.tmp

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class YESAudioRecorder(
    private val onRecorded: (ParcelFileDescriptor?) -> Unit
) {
    private val maxRecordSize =60000//67200
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
    private var pipe: Array<ParcelFileDescriptor>? = null
    private var outputStream: ParcelFileDescriptor.AutoCloseOutputStream? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
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
        var bytesWrite = 0
        job = coroutineScope.launch {
            //while (isReading && ((bytesWrite + byteArray.size) < maxRecordSize)) {
             while (true) {
                val bytesRead = rec?.read(byteArray, 0, byteArray.size) ?: 0
                bytesWrite += bytesRead
              //  println("bytesWrite$bytesWrite")
                if (bytesRead > 0) {
                    outputStream?.write(byteArray, 0, bytesRead)

                }
                 if (bytesWrite>maxRecordSize){
                     outputStream?.flush()
                 }
            }
            println("sending to recognition")
           /* outputStream?.flush()
            outputStream?.close()
            withContext(Dispatchers.Main) {
                onRecorded(pipe?.get(0))
            }*/

        }


        val tmp = 0

        onRecorded(pipe?.get(0))
    }

    fun stop() {
        isReading = false
        job?.cancel()
        ///////////////
        val c=job?.isCancelled
        val a=job?.isActive
        val co=job?.isCompleted
        //////////////
        rec?.stop()
        rec?.release()
        rec = null
        onRecorded(pipe?.get(0))
        // return pipe?.get(0)
    }
}