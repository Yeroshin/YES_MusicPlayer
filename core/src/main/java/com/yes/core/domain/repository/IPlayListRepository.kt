package com.yes.core.domain.repository

import com.yes.core.domain.models.PlayList
import com.yes.core.domain.models.Session

interface IPlayListRepository {
    fun addPlaylist(playList: PlayList)
    fun getPlaylist(id:Int)
    fun getAllPlaylists()
}