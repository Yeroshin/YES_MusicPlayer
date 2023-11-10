package com.yes.playlistfeature.data.repository

import android.util.Log
import android.widget.Toast
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.playlistfeature.data.mapper.Mapper
import com.yes.playlistfeature.domain.entity.Track
import java.io.File

class PlayerRepository(
    private val mapper: Mapper,
    private val playerDataSource: PlayerDataSource
) {
    fun setTracks(tracks: List<Track>) {
        playerDataSource.setTracks(
            tracks.map {
                mapper.mapToMediaItem(it)
            }
        )
    }
}