package com.yes.player.presentation.mapper

import com.yes.player.domain.model.DurationCounter
import com.yes.player.domain.model.PlayerState
import com.yes.player.domain.model.Playlist
import com.yes.player.domain.model.VisualizerData
import com.yes.player.presentation.model.PlayerStateUI
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow

class MapperUI {
    fun map(durationCounter: DurationCounter): PlayerStateUI{
        return PlayerStateUI(
            durationCounter = formatTime(durationCounter.data),
            progress = durationCounter.data.toInt()
        )
    }
    fun map(playlist: Playlist): PlayerStateUI{
        return PlayerStateUI(
            playListName = playlist.name

        )
    }
    private fun normalizeAmplitudesForVisualization(amplitudes: DoubleArray): DoubleArray {
        val minDb = -60.0 // минимальное значение в децибелах для нормализации
        val maxDb = 0.0 // максимальное значение в децибелах для нормализации
        val minAmp = 10.0.pow(minDb / 20.0) // преобразование минимального значения в амплитуду
        val maxAmp = 10.0.pow(maxDb / 20.0) // преобразование максимального значения в амплитуду

        val normalizedAmplitudes = DoubleArray(amplitudes.size)
        for (i in amplitudes.indices) {
            val amplitude = amplitudes[i]
            val db = 20.0 * log10(amplitude) // преобразование амплитуды в децибелы
            val normalizedDb = (db - minDb) / (maxDb - minDb) // нормализация значения в децибелах
            normalizedAmplitudes[i] = (normalizedDb * (maxAmp - minAmp)) + minAmp // преобразование нормализованного значения обратно в амплитуду
        }
        return normalizedAmplitudes
    }
    private fun convertToLogScale(powerValues: DoubleArray): DoubleArray {
        val maxPower = powerValues.maxOrNull() ?: 1.0 // Значение по умолчанию, если массив пустой
        val minNonZeroPower = powerValues.filter { it > 0.0 }.minOrNull() ?: maxPower
        val scaleFactor = if (maxPower.isInfinite()) 0.0 else 1.0 / ln(maxPower / minNonZeroPower)
        return powerValues.map { if (it.isInfinite()) 0.0 else ln(it / minNonZeroPower) * scaleFactor }.toDoubleArray()
    }
    fun map(visualizerData: VisualizerData): DoubleArray{
       /* val targetFrequencies = floatArrayOf(
            20f, 40f, 80f, 160f, 315f, 630f, 1250f, 2500f, 5000f, 10000f, 20000f
        )*/
        val targetFrequencies = floatArrayOf(
            31F, 62F, 125F, 250F, 500F, 1000F, 2000F, 4000F, 8000F, 16000F,
        )
        val frequencies = doubleArrayOf(31.0, 62.0, 125.0, 250.0, 500.0, 1000.0, 2000.0, 4000.0, 8000.0, 16000.0)
        val valuesForFrequencies = DoubleArray(frequencies.size)
        for (i in frequencies.indices) {
            val index = (frequencies[i] / 44100.0 * visualizerData.magnitudes.size).toInt()
            valuesForFrequencies[i] = visualizerData.magnitudes[index]
        }
        return convertToLogScale(valuesForFrequencies)
      //  return normalizeAmplitudesForVisualization(valuesForFrequencies)
// 31 62 125 250 500 1000 2000 4000 8000 16000
       /* return List(targetFrequencies.size) { i ->
            val targetFrequency = targetFrequencies[i]
            val closestIndex = visualizerData.frequencies.indices.minByOrNull {
                abs(visualizerData.frequencies[it] - targetFrequency)
            } ?: 0
            visualizerData. magnitudes[closestIndex]
        }*/
      /*  return sortedMapOf(
                20 to magnitudesAtTargetFrequencies[1], // 40 Гц
                40 to magnitudesAtTargetFrequencies[2], // 80 Гц
                80 to magnitudesAtTargetFrequencies[4], // 160 Гц
                160 to magnitudesAtTargetFrequencies[7], // 315 Гц
                315 to magnitudesAtTargetFrequencies[14], // 630 Гц
                630 to magnitudesAtTargetFrequencies[28], // 1250 Гц
                1250 to magnitudesAtTargetFrequencies[56], // 2500 Гц
                2500 to magnitudesAtTargetFrequencies[111], // 5000 Гц
                5000 to magnitudesAtTargetFrequencies[222], // 10000 Гц
                10000 to magnitudesAtTargetFrequencies[444], // 20000 Гц
                )*/
    }
    fun map(playerState: PlayerState): PlayerStateUI{
        return PlayerStateUI(
            trackTitle = playerState.artist?.let { artist ->
                artist+playerState.title?.let { " - $it" } } ?: playerState.title,
            stateBuffering = playerState.stateBuffering,
            durationInt = playerState.duration?.toInt(),
            duration = playerState.duration?.let {
                formatTime(playerState.duration)
            }
        )
    }
    private fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt() % 60
        val minutes = (milliseconds / (1000 * 60) % 60).toInt()
        return String.format("%02d:%02d",  minutes, seconds)
    }
}