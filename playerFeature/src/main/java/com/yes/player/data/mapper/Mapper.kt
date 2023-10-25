package com.yes.player.data.mapper


import androidx.media3.common.MediaMetadata
import com.yes.core.repository.entity.PlayListEntity
import com.yes.player.domain.model.DurationCounter
import com.yes.player.domain.model.MediaInfo
import com.yes.player.domain.model.Playlist

class Mapper {
    fun map(playlist: PlayListEntity): Playlist {
        return Playlist(
            playlist.id ?: 0,
            playlist.name,
        )
    }

    fun map(value: Long): DurationCounter {
        return DurationCounter(
            value
        )
    }

    fun map(metadata: MediaMetadata): MediaInfo {
        return MediaInfo(
            metadata.albumTitle?.toString(),
            metadata.artist?.toString(),
            metadata.title?.toString()
        )
    }

}