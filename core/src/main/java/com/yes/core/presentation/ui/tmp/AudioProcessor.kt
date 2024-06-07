package com.yes.core.presentation.ui.tmp

import android.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.audio.TeeAudioProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.nio.ByteOrder

@UnstableApi
class AudioProcessor : TeeAudioProcessor.AudioBufferSink {
    private var audioBufferSinkListener: ((buffer: ByteBuffer) -> Unit)? = null
    fun setListener(listener: (buffer: ByteBuffer) -> Unit) {
        audioBufferSinkListener = listener
    }

    private var sampleRateHz: Int = 0
    private var channelCount: Int = 0
    private var encoding: Int = 0
    override fun flush(sampleRateHz: Int, channelCount: Int, encoding: Int) {
        Log.d(": ", "waveformbytearray is not null.");
        this.sampleRateHz = sampleRateHz
        this.channelCount = channelCount
        this.encoding = encoding
    }
    private val processorScope = CoroutineScope(Dispatchers.Default)
    override fun handleBuffer(buffer: ByteBuffer) {
        // Apply fft &
        // pass the buffer data to your visualizer.
        Log.d(": ", "waveformbytearray is not null.");
        processorScope.launch {
            audioBufferSinkListener?.let {
                it(
                    convertByteBuffer(
                        copyByteBuffer(buffer),
                        channelCount,
                        sampleRateHz,
                        1,
                        16000
                    )
                )

            }
        }
       /* audioBufferSinkListener?.let {
            convertByteBuffer(
                buffer,
                channelCount,
                sampleRateHz,
                1,
                16000
            )
        }*/
    }
    private fun copyByteBuffer(original: ByteBuffer): ByteBuffer {
        val copy = ByteBuffer.allocate(original.capacity())
        original.rewind()
        copy.put(original)
        copy.flip()
        return copy
    }

    private fun convertByteBuffer(inputBuffer: ByteBuffer, inputChannels: Int, inputSampleRate: Int,
                                  outputChannels: Int, outputSampleRate: Int): ByteBuffer {
        val outputBuffer = ByteBuffer.allocate((inputBuffer.remaining() * outputSampleRate / inputSampleRate).toInt())
        outputBuffer.order(ByteOrder.LITTLE_ENDIAN)

        val sampleRateRatio = inputSampleRate.toDouble() / outputSampleRate.toDouble()
        var i = 0.0

        while (i < inputBuffer.remaining()) {
            inputBuffer.position(i.toInt())

            if (outputBuffer.remaining() < 2 * outputChannels) {
                break // Прекращаем цикл, если в outputBuffer не хватает места для записи outputSample
            }

            for (channel in 0 until outputChannels) {
                val sample = inputBuffer.getShort()
                val outputSample = (sample.toDouble() * sampleRateRatio).toInt().toShort()
                outputBuffer.putShort(outputSample)
            }

            i = i + 2 * inputChannels * sampleRateRatio
        }

        outputBuffer.flip()
        return outputBuffer
    }

}