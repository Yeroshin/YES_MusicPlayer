package com.yes.player.data.repository

import android.media.audiofx.Visualizer
import com.yes.player.data.factory.VisualizerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VisualizerRepository(
    private val visualizerFactory: VisualizerFactory
) {
    private val _fft = MutableStateFlow<ByteArray?>(
        byteArrayOf(0x48, 101, 108, 108, 111)
    )
    private val fftB: StateFlow<ByteArray?> = _fft
    fun subscribeVisualizer(audioSessionId: Int): Flow<ByteArray?> {
        visualizerFactory.createVisualizer(audioSessionId).setDataCaptureListener(
            object : Visualizer.OnDataCaptureListener {
                override fun onWaveFormDataCapture(
                    visualizer: Visualizer?,
                    waveform: ByteArray?,
                    samplingRate: Int
                ) {

                }

                override fun onFftDataCapture(
                    visualizer: Visualizer?,
                    fft: ByteArray?,
                    samplingRate: Int
                ) {
                    _fft.value = fft
                }
            },
            Visualizer.getMaxCaptureRate() / 2, false, true
        )
        return fftB
    }
}