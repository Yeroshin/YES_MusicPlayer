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
    override suspend fun run(params: Unit?): DomainResult<Flow<VisualizerData>> {
       // val sampleRate = 44100000/2000
        return DomainResult.Success(
            visualizerRepository.subscribeVisualizer().map { visualizerEntity ->
                visualizerEntity.fft?.let {

                    val n: Int = visualizerEntity.fft.size
                    val magnitudes = FloatArray(n / 2 + 1)

                    magnitudes[0] = abs(visualizerEntity.fft[0].toFloat())  // DC
                    magnitudes[n / 2] = abs(visualizerEntity.fft[1].toFloat()) // Nyquist

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
