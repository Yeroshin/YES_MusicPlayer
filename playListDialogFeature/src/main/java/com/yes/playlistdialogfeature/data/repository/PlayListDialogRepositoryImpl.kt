package com.yes.playlistdialogfeature.data.repository

import com.yes.core.domain.repository.IPlayListDao
import com.yes.playlistdialogfeature.data.mapper.Mapper
import com.yes.playlistdialogfeature.domain.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListDialogRepositoryImpl(
    private val mapper: Mapper,
    private val playListDao: IPlayListDao,
) {
    fun subscribePlaylists(): Flow<List<Item>> {
        return playListDao.subscribePlaylists().map {
            it.map { item->
                mapper.map(item)
            }
        }
    }
    fun saveNewPlaylist(playListName:String):Long{
        return playListDao.savePlaylist(
            mapper.map(playListName)
        )
    }
    fun deletePlaylist(item:Item):Int{
        return playListDao.deletePlaylist(
            mapper.map(item)
        )
    }
}