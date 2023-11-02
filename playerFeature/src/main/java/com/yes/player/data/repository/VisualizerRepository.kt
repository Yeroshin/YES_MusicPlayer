package com.yes.player.data.repository

import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.MEASUREMENT_MODE_PEAK_RMS
import android.media.audiofx.Visualizer.SCALING_MODE_NORMALIZED
import android.util.Log
import android.widget.Toast
import com.yes.player.data.factory.VisualizerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VisualizerRepository(
    private val visualizer:Visualizer
) {
    private val _fft = MutableStateFlow<ByteArray?>(
        byteArrayOf(0x48, 101, 108, 108, 111)
    )
    private val fftB: StateFlow<ByteArray?> = _fft
    private val captureListener = object : Visualizer.OnDataCaptureListener {
        override fun onWaveFormDataCapture(
            visualizer: Visualizer?,
            waveform: ByteArray?,
            samplingRate: Int
        ) {
            Log.d(": ", "waveformbytearray is not null.");
        }

        override fun onFftDataCapture(
            visualizer: Visualizer?,
            fft: ByteArray?,
            samplingRate: Int
        ) {
            _fft.value = fft
        }
    }
    fun subscribeVisualizer(): Flow<ByteArray?> {
       // visualizer.enabled = true
        visualizer.scalingMode = SCALING_MODE_NORMALIZED
        visualizer.measurementMode = MEASUREMENT_MODE_PEAK_RMS
        visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer.setDataCaptureListener(
            captureListener,
            Visualizer.getMaxCaptureRate() / 2, false, true
        )
        return fftB
    }
}