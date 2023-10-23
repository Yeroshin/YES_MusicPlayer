package com.yes.playlistfeature.presentation.mapper

import com.yes.playlistfeature.domain.entity.Track
import com.yes.playlistfeature.presentation.model.TrackUI

class MapperUI {
    fun map(track: Track): TrackUI {
        return TrackUI(
            track.id,
            track.artist+"-"+track.title,
            formatFileSize(track.size),
            formatTime(track.duration),
        )
    }
    fun map(track: TrackUI): Track {
        return Track(
            track.id,
            0,
            "",
            track.title,
            "",
            0,
            "",
            0,
            0
        )
    }
    private fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt() % 60
        val minutes = (milliseconds / (1000 * 60) % 60).toInt()
        return String.format("%02d:%02d",  minutes, seconds)
    }
    private fun formatFileSize(sizeInBytes: Long): String {
        val kiloByte = 1024
        val megaByte = kiloByte * 1024
        val gigaByte = megaByte * 1024

        return when {
            sizeInBytes < kiloByte -> "$sizeInBytes B"
            sizeInBytes < megaByte -> String.format("%.2f KB", sizeInBytes.toFloat() / kiloByte)
            sizeInBytes < gigaByte -> String.format("%.2f MB", sizeInBytes.toFloat() / megaByte)
            else -> String.format("%.2f GB", sizeInBytes.toFloat() / gigaByte)
        }
    }

}