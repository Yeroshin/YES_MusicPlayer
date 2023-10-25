package com.yes.player.presentation.mapper

import com.yes.player.domain.model.DurationCounter
import com.yes.player.domain.model.MediaInfo
import com.yes.player.domain.model.Playlist
import com.yes.player.presentation.model.InfoUI

class MapperUI {
    fun map(durationCounter: DurationCounter): InfoUI{
        return InfoUI(
            durationCounter = formatTime(durationCounter.data)
        )
    }
    fun map(playlist: Playlist): InfoUI{
        return InfoUI(
            playListName = playlist.name

        )
    }
    fun map(mediaInfo: MediaInfo): InfoUI{
        return InfoUI(
            trackTitle = mediaInfo.artist?.let { artist ->
                artist+mediaInfo.title?.let { " - $it" } } ?: mediaInfo.title
        )
    }
    private fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt() % 60
        val minutes = (milliseconds / (1000 * 60) % 60).toInt()
        return String.format("%02d:%02d",  minutes, seconds)
    }
}