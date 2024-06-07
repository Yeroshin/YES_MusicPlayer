package com.yes.player.data.mapper


import com.yes.core.data.entity.PlayListDataBaseEntity
import com.yes.core.data.entity.PlayerStateDataSourceEntity
import com.yes.player.domain.model.DurationCounter
import com.yes.player.domain.model.PlayerState
import com.yes.player.domain.model.Playlist

class Mapper {
    fun map(playlist: PlayListDataBaseEntity): Playlist {
        return Playlist(
            playlist.id ?: 1,
            playlist.name,
        )
    }

    fun map(value: Long): DurationCounter {
        return DurationCounter(
            value
        )
    }

    fun map(playerStateDataSourceEntity: PlayerStateDataSourceEntity): PlayerState {
        return PlayerState(
            playerStateDataSourceEntity.mediaMetadata?.albumTitle?.toString(),
            playerStateDataSourceEntity.mediaMetadata?.artist?.toString(),
            playerStateDataSourceEntity.mediaMetadata?.title?.toString(),
            playerStateDataSourceEntity.stateBuffering,
            playerStateDataSourceEntity.duration,
            playerStateDataSourceEntity.isPlaying
        )
    }

}