package com.yes.trackdialogfeature.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity
import com.yes.trackdialogfeature.data.repository.entity.TrackEntity

@Dao
interface PlayListDao {
    @Insert
    fun savePlaylist(playlist: PlayListEntity)
    @Query("SELECT * FROM playlists WHERE name =:playlistName")
    fun getPlaylist(playlistName:String): PlayListEntity
    @Update
    fun updatePlaylist(playlist: PlayListEntity)

    @Insert
    fun saveTracks(tracks:List<TrackEntity>)
    @Query("SELECT * FROM tracks WHERE playlistName =:playlistName")
    fun getTracks(playlistName:String): List<TrackEntity>
}