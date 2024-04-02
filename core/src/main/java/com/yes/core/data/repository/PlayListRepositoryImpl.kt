package com.yes.core.data.repository

import com.yes.core.data.mapper.Mapper
import com.yes.core.domain.entity.Track
import com.yes.core.domain.repository.IPlayListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(
    private val mapper: Mapper,
    private val playListDao: IPlayListDao,
) {
    fun subscribeTracksWithPlaylistId(playlistId: Long): Flow<List<Track>> {
        return playListDao.subscribeTracksWithPlaylistId(playlistId).map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }
}