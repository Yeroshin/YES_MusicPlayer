package com.yes.player.data.repository

import com.yes.core.data.factory.RendererFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import java.nio.ByteBuffer

class VisualizerRepository(
   private val rendererFactory: RendererFactory
) {

  /*  private val captureListener = object : Visualizer.OnDataCaptureListener {
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
    }*/
    init {
       /* visualizer.scalingMode = SCALING_MODE_NORMALIZED
        visualizer.measurementMode = MEASUREMENT_MODE_PEAK_RMS
        visualizer.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer.setDataCaptureListener(
            captureListener,Visualizer.getMaxCaptureRate() / 2, false, true)*/
           /* object : Visualizer.OnDataCaptureListener {
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
            },
            Visualizer.getMaxCaptureRate() / 2, false, true
        )*/
    }


    fun subscribeVisualizer(): Flow<ByteArray> {
      return rendererFactory.subscribeByteBuffer()
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