package com.yes.player.data.repository

import androidx.media3.extractor.mp4.Track
import com.yes.core.domain.repository.IPlayListDao
import com.yes.player.data.mapper.Mapper
import com.yes.player.domain.model.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl (
    private val mapper: Mapper,
    private val playListDao: IPlayListDao,
){
    fun getPlaylist(playlistId: Long): Playlist {
       return  mapper.map(
                playListDao.getPlaylist(playlistId)
            )
    }

}