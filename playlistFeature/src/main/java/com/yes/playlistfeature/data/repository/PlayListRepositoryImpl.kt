package com.yes.playlistfeature.data.repository

import com.yes.core.domain.repository.IPlayListDao
import com.yes.playlistfeature.data.mapper.Mapper
import com.yes.playlistfeature.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(
    private val mapper: Mapper,
    private val playListDao: IPlayListDao,
) {
    fun subscribeTracksWithPlaylistId(playlistId: Long): Flow<List<Track>> {
        return playListDao.subscribeTracksWithPlaylistId(playlistId).map {
            it.map { item->
                mapper.map(item)
            }
        }
    }
}