package com.yes.player.data.repository

import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.MEASUREMENT_MODE_PEAK_RMS
import android.media.audiofx.Visualizer.SCALING_MODE_NORMALIZED
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class VisualizerRepository(
    private val visualizer: Visualizer
) {
    private val _byteArray = MutableStateFlow<ByteArray?>(null)
    private val byteArray: StateFlow<ByteArray?> = _byteArray
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
            _byteArray.value = fft?.clone()
        }
    }

    init {
        visualizer.scalingMode = SCALING_MODE_NORMALIZED
        visualizer.measurementMode = MEASUREMENT_MODE_PEAK_RMS
        visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer.setDataCaptureListener(
            captureListener,
            Visualizer.getMaxCaptureRate() / 2,
            false,
            true
        )
        /*  val job = CoroutineScope(Dispatchers.Default).launch {
              while (true) {
                  delay(1000) // ждем 1 секунду
                   byteArray.value = visualizer.getFft() // отправляем новое значение в StateFlow
              }
          }*/

    }

    fun subscribeVisualizer(): Flow<ByteArray?> {


        if (!visualizer.enabled) {
            visualizer.enabled = true
        }

        return byteArray
    }


}
// Online radio:
//val uri = Uri.parse("http://listen.livestreamingservice.com/181-xsoundtrax_128k.mp3")
// 1 kHz test sound:
// val uri = Uri.parse("https://www.mediacollege.com/audio/tone/files/1kHz_44100Hz_16bit_05sec.mp3")
// 10 kHz test sound:
// val uri = Uri.parse("https://www.mediacollege.com/audio/tone/files/10kHz_44100Hz_16bit_05sec.mp3")
// Sweep from 20 to 20 kHz
// val uri = Uri.parse("https://www.churchsoundcheck.com/CSC_sweep_20-20k.wav")