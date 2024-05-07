 package com.yes.core.presentation.ui.tmp

import android.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.audio.TeeAudioProcessor
import java.nio.ByteBuffer
 @UnstableApi
class AudioProcessor: TeeAudioProcessor.AudioBufferSink {
    private var audioBufferSinkListener:((buffer: ByteBuffer)->Unit)?=null
    fun setListener(listener:(buffer: ByteBuffer)->Unit){
        audioBufferSinkListener=listener
    }
    override fun flush(sampleRateHz: Int, channelCount: Int, encoding: Int) {
        Log.d(": ", "waveformbytearray is not null.");

    }

    override fun handleBuffer(buffer: ByteBuffer) {
        // Apply fft &
        // pass the buffer data to your visualizer.
        Log.d(": ", "waveformbytearray is not null.");
        audioBufferSinkListener?.let {
            it(buffer)
        }
    }


}