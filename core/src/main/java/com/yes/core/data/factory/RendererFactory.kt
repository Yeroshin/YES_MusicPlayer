package com.yes.core.data.factory

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.audio.DefaultAudioSink
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.audio.TeeAudioProcessor
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.nio.ByteBuffer
import kotlin.random.Random

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class RendererFactory(
    context: Context,
) : DefaultRenderersFactory(context) {

    // byteArrayOf(0x48, 101, 108, 108, 111)
    private val _byteBuffer = MutableStateFlow<ByteBuffer?>(null)
    private val byteBuffer: StateFlow<ByteBuffer?> = _byteBuffer
    fun subscribeByteBuffer(): Flow<ByteBuffer?> {
        return byteBuffer
    }

    private val _byteArray = MutableStateFlow<ByteArray?>(null)
    private val byteArray: StateFlow<ByteArray?> = _byteArray
    fun subscribeByteArray(): Flow<ByteArray?> {
        return byteArray
    }

     val _tmp = MutableStateFlow<Int>(0)
    val tmp: StateFlow<Int> = _tmp
    private val emitterScope = CoroutineScope(Dispatchers.Default)
    private val audioBufferSink = object : TeeAudioProcessor.AudioBufferSink {
        override fun flush(sampleRateHz: Int, channelCount: Int, encoding: Int) {
            Log.d(": ", "waveformbytearray is not null.");

        }

        override fun handleBuffer(buffer: ByteBuffer) {

            try {
                /*  val bufferCopy = buffer.duplicate()
                  val bufferSize = buffer.remaining()
                  val byteArray = ByteArray(bufferSize)
                  bufferCopy.get(byteArray)
                  CoroutineScope(Dispatchers.IO).launch {
                      _byteArray.value=byteArray
                  }*/
                /////////////
                /*  val bufferCopy = ByteBuffer.allocate(buffer.remaining())
                  bufferCopy.put(buffer)
                  bufferCopy.flip()
                  CoroutineScope(Dispatchers.Main).launch {
                      _byteArray.emit(bufferCopy.array())
                  }*/
                /////////////
                /////////////
                val bufferCopy = ByteBuffer.allocate(buffer.remaining())
                bufferCopy.put(buffer)
                bufferCopy.flip()
                _tmp.value = Random.nextInt(10)
                emitterScope.launch {
                    val randomBuffer = ByteBuffer.allocate(1024)
                    val randomBytes = ByteArray(1024)
                    Random.nextBytes(randomBytes)
                    randomBuffer.put(randomBytes)
                    randomBuffer.flip()
                    _byteBuffer.value = randomBuffer
                    _tmp.emit(Random.nextInt(10))
                    _tmp.value=Random.nextInt(10)
                }
                /////////////
            } catch (e: IOException) {
                Log.e(
                    "WavFileAudioBu",
                    "Error writing data",
                    e
                )
            } catch (
                e: IllegalStateException
            ) {
                Log.e(
                    "WavFileAudioBu",
                    "Error writing data",
                    e
                )
            }


        }

    }


    override fun buildAudioRenderers(
        context: Context,
        extensionRendererMode: Int,
        mediaCodecSelector: MediaCodecSelector,
        enableDecoderFallback: Boolean,
        audioSink: AudioSink,
        eventHandler: Handler,
        eventListener: AudioRendererEventListener,
        out: ArrayList<Renderer>
    ) {

        val audioProcessor = arrayOf(
            TeeAudioProcessor(audioBufferSink)
        )
        out.add(
            MediaCodecAudioRenderer(
                context,
                mediaCodecSelector,
                enableDecoderFallback,
                eventHandler,
                eventListener,
                DefaultAudioSink.Builder(context)
                    //  .setAudioCapabilities(AudioCapabilities.getCapabilities(context))
                    .setAudioProcessors(audioProcessor)
                    .build()
            )
        )
        super.buildAudioRenderers(
            context,
            extensionRendererMode,
            mediaCodecSelector,
            enableDecoderFallback,
            audioSink,
            eventHandler,
            eventListener,
            out
        )
    }
}