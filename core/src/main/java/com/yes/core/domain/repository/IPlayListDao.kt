package com.yes.core.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yes.core.repository.entity.PlayListEntity
import com.yes.core.repository.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IPlayListDao {
    @Insert
    fun savePlaylist(playlist: PlayListEntity): Long

    @Query("SELECT * FROM playlists WHERE name =:playlistName")
    fun getPlaylist(playlistName: String): PlayListEntity

    @Query("SELECT * FROM playlists")
    fun subscribePlaylists(): Flow<List<PlayListEntity>>

    /*  @Update
      fun updatePlaylist(playlist: PlayList)*/
    @Delete
    fun deletePlaylist(playListEntity: PlayListEntity):Int


    @Insert
    fun saveTracks(trackEntities: List<TrackEntity>): List<Long>

    @Query("SELECT * FROM tracks WHERE playlistName =:playlistName")
    fun getTracks(playlistName: String): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE id =:id")
    fun subscribeTracksWithPlaylistId(id: Long):Flow< List<TrackEntity>>
}