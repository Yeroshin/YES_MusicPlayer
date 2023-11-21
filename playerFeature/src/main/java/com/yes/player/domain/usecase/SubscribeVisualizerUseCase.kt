package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.VisualizerRepository
import com.yes.player.domain.model.VisualizerData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.sqrt

class SubscribeVisualizerUseCase(
    dispatcher: CoroutineDispatcher,
    private val visualizerRepository: VisualizerRepository
) : UseCase<Unit?, Flow<VisualizerData>>(dispatcher) {
    data class Complex(val real: Double, val imaginary: Double)

    private fun convertToComplexArray(fft: ByteArray): Array<Complex> {
        val complexArray = Array(fft.size / 2+1) { Complex(0.0, 0.0) }
        for (i in 0 until fft.size / 2+1) {
            val real = fft[2 * i].toDouble()
            val imaginary = fft[2 * i + 1].toDouble()
            complexArray[i] = Complex(real, imaginary)
        }
        return complexArray
    }

    private fun calculateAmplitudes(fftAsComplex: Array<Complex>): DoubleArray {
        val amplitudes = DoubleArray(fftAsComplex.size / 2)
        for (i in 0 until fftAsComplex.size / 2) {
            val real = fftAsComplex[i].real
            val imaginary = fftAsComplex[i].imaginary
            amplitudes[i] = sqrt(real * real + imaginary * imaginary)
        }
        return amplitudes
    }

    fun getValuesForFrequencies(amplitudes: DoubleArray): DoubleArray {
        val frequencies = doubleArrayOf(31.0, 62.0, 125.0, 250.0, 500.0, 1000.0, 2000.0, 4000.0, 8000.0, 16000.0)
        val valuesForFrequencies = DoubleArray(frequencies.size)
        for (i in frequencies.indices) {
            val index = (frequencies[i] / 44100.0 * amplitudes.size).toInt()
            valuesForFrequencies[i] = amplitudes[index]
        }
        return valuesForFrequencies
    }
    override suspend fun run(params: Unit?): DomainResult<Flow<VisualizerData>> {
       // val sampleRate = 44100000/2000
        return DomainResult.Success(
            visualizerRepository.subscribeVisualizer().map { visualizerEntity ->
                visualizerEntity.fft?.let {
                   /* val magnitudes=calculateAmplitudes(
                        convertToComplexArray(fft)
                    )*/

                    val n: Int = visualizerEntity.fft.size
                    val magnitudes = FloatArray(n / 2 + 1)
                    val phases = FloatArray(n / 2 + 1)
                    magnitudes[0] = abs(visualizerEntity.fft[0].toFloat())  // DC

                    magnitudes[n / 2] = abs(visualizerEntity.fft[1].toFloat()) // Nyquist

                    phases[0] = 0.also { phases[n / 2] = 0F }.toFloat()
                    for (k in 1 until n / 2) {
                        val i = k * 2
                        magnitudes[k] = hypot(visualizerEntity.fft[i].toFloat(), visualizerEntity.fft[i + 1].toFloat())
                    }


                    VisualizerData(
                        magnitudes=magnitudes,
                        samplingRate = visualizerEntity.samplingRate
                    )
                }?: VisualizerData()
            }
        )
    }
}

//20 Гц, 40 Гц, 80 Гц, 160 Гц, 315 Гц, 630 Гц, 1250 Гц, 2500 Гц, 5000 Гц, 10000 Гц, 20000 Гц.
