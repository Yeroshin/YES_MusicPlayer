package com.yes.core.presentation.ui.tmp

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Handler
import android.os.Looper
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

@SuppressLint("MissingPermission")
class VoskSpeechService(
    private val recognizer: Recognizer,
    private val sampleRate: Float
) : SpeechService(
    recognizer,
    sampleRate
) {

    private val bufferSizeSeconds = 0.2f
   // private val bufferSize: Int = (sampleRate * bufferSizeSeconds).toInt()
   private val bufferSize: Int =3342
    private val recorder: AudioRecord = AudioRecord(
        MediaRecorder.AudioSource.CAMCORDER,
        sampleRate.toInt(),
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT,
        bufferSize * 2
    )


    private var recognizerThread: RecognizerThread? = null

    private val mainHandler = Handler(Looper.getMainLooper())

    init {

        if (recorder.state == AudioRecord.STATE_UNINITIALIZED) {
            recorder.release()
            throw IOException("Failed to initialize recorder. Microphone might be already in use.")
        }
    }

    override fun startListening(listener: RecognitionListener): Boolean {
        if (recognizerThread != null) return false

        recognizerThread = RecognizerThread(listener)
        recognizerThread?.start()
        return true
    }

    override fun startListening(listener: RecognitionListener, timeout: Int): Boolean {
        if (recognizerThread != null) return false

        recognizerThread = RecognizerThread(listener, timeout)
        recognizerThread?.start()
        return true
    }

    private fun stopRecognizerThread(): Boolean {
        if (recognizerThread == null) return false

        try {
            recognizerThread?.interrupt()
            recognizerThread?.join()
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        recognizerThread = null
        return true
    }

    override fun stop(): Boolean {
        return stopRecognizerThread()
    }

    override fun cancel(): Boolean {
        recognizerThread?.setPause(true)
        return stopRecognizerThread()
    }

    override fun shutdown() {
        recorder.release()
    }

    override fun setPause(paused: Boolean) {
        recognizerThread?.setPause(paused)
    }

    override fun reset() {
        recognizerThread?.reset()
    }

    /////////////



    var noiseBufferUpdated = false
    val noiseArray=ShortArray(bufferSize)
    val tmpArray=ShortArray(bufferSize)
    fun setNoiseBuffer(data: ByteBuffer) {
        val shortArray = ShortArray(data.remaining())
        data.rewind()
        data.asShortBuffer().get(shortArray)
        val remaining=bufferSize-tmpArray.lastIndex

    }
    fun subtractShortArrayFromByteBuffer(shortArray1: ShortArray, byteBuffer: ByteBuffer) {
        val tempBuffer = ByteBuffer.allocate(byteBuffer.capacity())
        byteBuffer.mark()

        for (i in shortArray1.indices) {
            if (byteBuffer.remaining() < 2) {
                byteBuffer.reset()
                break
            }

            val value = shortArray1[i]
            val byte1 = byteBuffer.get().toInt() and 0xFF
            val byte2 = byteBuffer.get().toInt() and 0xFF

            val result = value - ((byte1 shl 8) or byte2).toShort()
            tempBuffer.putShort(result.toShort())
        }

        byteBuffer.clear()
        tempBuffer.flip()
        byteBuffer.put(tempBuffer)
    }
    ////////////

    private inner class RecognizerThread(
        private val listener: RecognitionListener,
        private val timeout: Int = -1
    ) : Thread() {

        private var remainingSamples = timeout * sampleRate.toInt() / 1000
        private var paused = false
        private var reset = false


        override fun run() {
            recorder.startRecording()
            if (recorder.recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                recorder.stop()
                val ioe =
                    IOException("Failed to start recording. Microphone might be already in use.")
                mainHandler.post { listener.onError(ioe) }
            }

            val buffer = ShortArray(bufferSize)

            while (!isInterrupted && (timeout == -1 || remainingSamples > 0)) {
                val s = bufferSize
                val nread = recorder.read(buffer, 0, buffer.size)
////////////////////////////////////////
               /* if (noiseBufferUpdated){
                    subtractShortArrayFromByteBuffer(buffer,byteBuffer)

                    noiseBufferUpdated=false
                }*/
                //////////////////////////////////////////////
                if (paused) continue

                if (reset) {
                    recognizer.reset()
                    reset = false
                }

                if (nread < 0) throw RuntimeException("error reading audio buffer")

                if (recognizer.acceptWaveForm(buffer, nread)) {
                    val result = recognizer.result
                    mainHandler.post { listener.onResult(result) }
                } else {
                    val partialResult = recognizer.partialResult
                    mainHandler.post { listener.onPartialResult(partialResult) }
                }

                if (timeout != -1) {
                    remainingSamples -= nread
                }
            }

            recorder.stop()

            if (!paused) {
                if (timeout != -1 && remainingSamples <= 0) {
                    mainHandler.post { listener.onTimeout() }
                } else {
                    val finalResult = recognizer.getFinalResult()
                    mainHandler.post { listener.onFinalResult(finalResult) }
                }
            }
        }

        fun setPause(paused: Boolean) {
            this.paused = paused
        }

        fun reset() {
            reset = true
        }
    }
}
