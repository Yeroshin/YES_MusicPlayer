package com.yes.core.data.factory

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.media3.common.audio.AudioProcessor
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.Renderer
import androidx.media3.exoplayer.audio.AudioCapabilities
import androidx.media3.exoplayer.audio.AudioRendererEventListener
import androidx.media3.exoplayer.audio.AudioSink
import androidx.media3.exoplayer.audio.DefaultAudioSink
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer
import androidx.media3.exoplayer.audio.TeeAudioProcessor
import androidx.media3.exoplayer.drm.DrmSessionManager
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.nio.ByteBuffer
import java.util.ArrayList

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class RendererFactory(context: Context, private val audioBufferSink : TeeAudioProcessor.AudioBufferSink ) :
    DefaultRenderersFactory(context) {
   /* private val audioBufferSink=object : TeeAudioProcessor.AudioBufferSink {
        override fun flush(sampleRateHz: Int, channelCount: Int, encoding: Int) {
            Log.d(": ", "waveformbytearray is not null.");

        }

        override fun handleBuffer(buffer: ByteBuffer) {
            _byteBuffer.value=buffer.array()
            Log.d(": ", "waveformbytearray is not null.");

        }

    }*/
   // byteArrayOf(0x48, 101, 108, 108, 111)
    private val _byteBuffer = MutableStateFlow(byteArrayOf(0x48, 101, 108, 108, 111))
    private val byteBuffer: StateFlow<ByteArray> = _byteBuffer
fun subscribeByteBuffer(): Flow<ByteArray> {
    return byteBuffer
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

        val audioProcessor = Array<AudioProcessor>(1) {
            TeeAudioProcessor(audioBufferSink)
        }
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