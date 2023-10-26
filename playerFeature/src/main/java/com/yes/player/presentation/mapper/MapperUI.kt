package com.yes.player.presentation.mapper

import com.yes.player.domain.model.DurationCounter
import com.yes.player.domain.model.PlayerState
import com.yes.player.domain.model.Playlist
import com.yes.player.presentation.model.PlayerStateUI

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