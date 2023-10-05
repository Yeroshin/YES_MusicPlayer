package com.yes.core.domain.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yes.core.domain.models.PlayList
import com.yes.core.domain.models.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface IPlayListDao {
    @Insert
    fun savePlaylist(playlist: PlayList)
    @Query("SELECT * FROM playlists WHERE name =:playlistName")
    fun getPlaylist(playlistName:String): PlayList
    @Query("SELECT * FROM playlists")
   fun subscribePlaylists(): Flow<List<PlayList>>
    @Update
    fun updatePlaylist(playlist: PlayList)

    @Insert
    fun saveTracks(tracks:List<Track>):List<Long>
    @Query("SELECT * FROM tracks WHERE playlistName =:playlistName")
    fun getTracks(playlistName:String): List<Track>
}