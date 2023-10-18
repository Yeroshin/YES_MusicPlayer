package com.yes.player.data.repository

import com.yes.core.domain.repository.IPlayListDao
import com.yes.player.data.mapper.Mapper
import com.yes.player.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistRepositoryImpl (
    private val mapper: Mapper,
    private val playListDao: IPlayListDao,
){
    fun subscribeTracksWithPlaylistId(playlistId: Long): Flow<List<Track>> {
        return playListDao.subscribeTracksWithPlaylistId(playlistId).map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }
}