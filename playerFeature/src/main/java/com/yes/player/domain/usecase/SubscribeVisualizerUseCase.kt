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

class SubscribeVisualizerUseCase(
    dispatcher: CoroutineDispatcher,
    private val visualizerRepository: VisualizerRepository
) : UseCase<Unit?, Flow<VisualizerData>>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Flow<VisualizerData>> {
        val sampleRate = 44100000/2000
        return DomainResult.Success(
            visualizerRepository.subscribeVisualizer().map { fft ->
                fft?.let {
                    val n: Int = fft.size
                    val magnitudes = FloatArray(n / 2 + 1)
                    val phases = FloatArray(n / 2 + 1)
                    magnitudes[0] = abs(fft[0].toFloat())  // DC

                    magnitudes[n / 2] = abs(fft[1].toFloat()) // Nyquist

                    phases[0] = 0.also { phases[n / 2] = it.toFloat() }.toFloat()
                    for (k in 1 until n / 2) {
                        val i = k * 2
                        magnitudes[k] = hypot(fft[i].toDouble(), fft[i + 1].toDouble()).toFloat()
                    }


                    val frequencies = FloatArray(n / 2 + 1) { k ->
                        k * sampleRate.toFloat() / n
                    }

                    VisualizerData(
                        magnitudes,
                        frequencies
                    )
                }?: VisualizerData()
            }
        )
    }
}

//20 Гц, 40 Гц, 80 Гц, 160 Гц, 315 Гц, 630 Гц, 1250 Гц, 2500 Гц, 5000 Гц, 10000 Гц, 20000 Гц.