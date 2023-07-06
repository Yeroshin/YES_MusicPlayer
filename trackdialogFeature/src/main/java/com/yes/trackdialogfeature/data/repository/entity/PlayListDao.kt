package com.yes.trackdialogfeature.data.repository.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity

@Dao
interface PlayListDao {
    @Insert
    fun insertPlaylist(playlist:PlayListEntity)
    @Query("SELECT * FROM playlists WHERE name =:playlistName")
    fun getPlaylist(playlistName:String): PlayListEntity
    @Update
    fun updatePlaylist(playlist:PlayListEntity)
}