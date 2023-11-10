package com.yes.player.presentation.mapper

import com.yes.player.domain.model.DurationCounter
import com.yes.player.domain.model.PlayerState
import com.yes.player.domain.model.Playlist
import com.yes.player.domain.model.VisualizerData
import com.yes.player.presentation.model.PlayerStateUI
import kotlin.math.abs

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
    fun map(visualizerData: VisualizerData): List<Float>{
        val targetFrequencies = floatArrayOf(
            20f, 40f, 80f, 160f, 315f, 630f, 1250f, 2500f, 5000f, 10000f, 20000f
        )

        return List(targetFrequencies.size) { i ->
            val targetFrequency = targetFrequencies[i]
            val closestIndex = visualizerData.frequencies.indices.minByOrNull {
                abs(visualizerData.frequencies[it] - targetFrequency)
            } ?: 0
            visualizerData. magnitudes[closestIndex]
        }
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