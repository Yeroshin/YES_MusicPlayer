package com.yes.player.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.player.data.repository.VisualizerRepository
import com.yes.player.domain.model.VisualizerData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.ByteBuffer
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

class SubscribeVisualizerUseCase(
    dispatcher: CoroutineDispatcher,
    private val visualizerRepository: VisualizerRepository
) : UseCase<Unit?, Flow<VisualizerData>>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Flow<VisualizerData>> {

        return DomainResult.Success(
            visualizerRepository.subscribeVisualizer().map { byteArray ->
                byteArray?.let {
                    val n = byteArray.size
                    val magnitudes = FloatArray(n / 2 + 1)
                    val phases = FloatArray(n / 2 + 1)
                    magnitudes[0] = abs(byteArray[0].toFloat() )     // DC
                    magnitudes[n / 2] = abs(byteArray[1].toFloat() ) // Nyquist
                    phases[0] = 0f
                    phases[n / 2] = 0f
                    for (k in 1 until n / 2) {
                        val i = k * 2
                        magnitudes[k] = hypot(byteArray[i].toDouble(), byteArray[i + 1].toDouble()).toFloat()
                        phases[k] = atan2(byteArray[i + 1].toDouble(), byteArray[i].toDouble()).toFloat()
                    }
                    VisualizerData(
                        magnitudes,
                        phases
                    )
                }?: VisualizerData()
            }
        )
    }
}

