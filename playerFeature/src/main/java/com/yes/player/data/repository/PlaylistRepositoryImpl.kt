package com.yes.player.data.repository

import com.yes.core.domain.repository.IPlayListDao
import com.yes.player.data.mapper.Mapper
import com.yes.player.domain.model.Playlist

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