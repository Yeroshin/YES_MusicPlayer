package com.yes.player.presentation.mapper

import androidx.media3.common.MediaItem
import com.yes.player.domain.model.Track

class Mapper {
    fun map(track: Track): MediaItem {
        return MediaItem.Builder().setUri(
            track.uri
        ).build()
    }
}