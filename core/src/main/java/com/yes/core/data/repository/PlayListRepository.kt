package com.yes.core.data.repository

import com.yes.core.domain.models.PlayList
import com.yes.core.domain.models.Session
import com.yes.core.domain.repository.IPlayListRepository

class PlayListRepository:IPlayListRepository {
    private lateinit var session: Session
    override fun addPlaylist(playList: PlayList) {
        TODO("Not yet implemented")
    }

    override fun getPlaylist(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllPlaylists() {
        TODO("Not yet implemented")
    }

}