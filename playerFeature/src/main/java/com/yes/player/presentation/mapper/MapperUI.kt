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

    private fun convertToLogScale(powerValues: FloatArray): FloatArray {
        val maxPower = powerValues.maxOrNull() ?: 1F // Значение по умолчанию, если массив пустой
        val minNonZeroPower = powerValues.filter { it > 0F }.minOrNull() ?: maxPower
        val scaleFactor = 1F / ln(maxPower / minNonZeroPower)
        return powerValues.map { power ->
            val logValue = ln(power / minNonZeroPower) * scaleFactor
            if (logValue.isNaN() || logValue.isInfinite()) {
                0F // Заменяем NaN или Infinity на 0 или другое значение по вашему выбору
            } else {
                logValue
            }
        }.toFloatArray()
    }
    fun map(visualizerData: VisualizerData): FloatArray{

        //val frequencies = floatArrayOf(31f, 62f, 125f, 250f, 500f, 1000f, 2000f, 4000f, 8000f, 16000f)
        val frequencies = floatArrayOf(20f, 50f, 80f, 125f, 200f, 315f, 500f, 800f, 1000f, 1250f,2000f,3150f,5000f,8000f,2500f,16000f)
        val valuesForFrequencies = FloatArray(frequencies.size)
        for (i in frequencies.indices) {
            val index = (frequencies[i] / visualizerData.samplingRate * visualizerData.magnitudes.size).toInt()
            valuesForFrequencies[i] = visualizerData.magnitudes[index]
        }
        return convertToLogScale(valuesForFrequencies)

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