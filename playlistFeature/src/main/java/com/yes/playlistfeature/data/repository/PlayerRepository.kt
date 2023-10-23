package com.yes.playlistfeature.data.repository

import com.yes.core.repository.dataSource.PlayerDataSource
import com.yes.playlistfeature.data.mapper.Mapper
import com.yes.playlistfeature.domain.entity.Track
import com.yes.playlistfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase

class PlayerRepository(
    private val mapper: Mapper,
    private val playerDataSource: PlayerDataSource
) {
    fun setTracks(tracks:List<Track>) {

        playerDataSource.setTracks(
            tracks.map {
                mapper.mapToMediaItem(it)
            }
        )
    }
}