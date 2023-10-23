package com.yes.player.data.mapper



import com.yes.core.repository.entity.PlayListEntity
import com.yes.player.domain.model.Playlist

class Mapper {
    fun map(playlist: PlayListEntity): Playlist {
        return Playlist(
            playlist.id?:0,
            playlist.name,
        )
    }

}