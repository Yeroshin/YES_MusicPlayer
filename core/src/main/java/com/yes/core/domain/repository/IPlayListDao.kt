package com.yes.core.domain.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yes.core.data.entity.PlayListDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IPlayListDao {
    @Query("SELECT COUNT(*) FROM playlists")
    fun getPlaylistCount(): Int
    @Insert
    fun savePlaylist(playlist: PlayListDataBaseEntity): Long

    @Query("SELECT * FROM playlists WHERE id =:id")
    fun getPlaylist(id: Long): PlayListDataBaseEntity

    @Query("SELECT * FROM playlists")
    fun subscribePlaylists(): Flow<List<PlayListDataBaseEntity>>

    /*  @Update
      fun updatePlaylist(playlist: PlayList)*/
    @Delete
    fun deletePlaylist(playListDataBaseEntity: PlayListDataBaseEntity):Int


    @Insert
    fun saveTracks(trackEntities: List<PlayListDataBaseTrackEntity>): List<Long>

    @Query("SELECT * FROM tracks WHERE playlistId =:playlistName")
    fun getTracks(playlistName: String): List<PlayListDataBaseTrackEntity>

    @Query("SELECT * FROM tracks WHERE playlistId =:id")
    fun subscribeTracksWithPlaylistId(id: Long):Flow< List<PlayListDataBaseTrackEntity>>

    @Delete
    fun deleteTrack(playListDataBaseTrackEntity: PlayListDataBaseTrackEntity):Int
    @Update
    fun updateTrack(playListDataBaseTrackEntity: PlayListDataBaseTrackEntity):Int

    @Query("DELETE FROM tracks WHERE playlistId = :playlistId")
    fun deleteTracksByPlaylistId(playlistId: Long)
}