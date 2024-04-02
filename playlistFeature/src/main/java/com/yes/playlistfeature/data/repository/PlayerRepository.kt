package com.yes.playlistfeature.data.repository

import android.util.Log
import android.widget.Toast
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.playlistfeature.data.mapper.Mapper
import com.yes.playlistfeature.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File

class PlayerRepository(
    private val mapper: Mapper,
    private val playerDataSource: PlayerDataSource
) {
    fun setTracks(tracks: List<Track>,index:Int) {
        playerDataSource.setTracks(
            tracks.map {
                mapper.mapToMediaItem(it)
            },
            index
        )
    }
    fun play(index:Int){
        playerDataSource.play(index)
    }
    fun getRepeatMode(): Boolean {
        return playerDataSource.getRepeatMode() != REPEAT_MODE_OFF
    }
    fun enableRepeatMode() {
        playerDataSource.setRepeatMode(REPEAT_MODE_ONE)
    }
    fun disableRepeatMode() {
        playerDataSource.setRepeatMode(REPEAT_MODE_OFF)
    }
    fun getShuffleMode(): Boolean {
        return playerDataSource.getShuffleMode()
    }
    fun enableShuffleMode() {
        playerDataSource.setShuffleMode(true)
    }

    fun disableShuffleMode() {
       playerDataSource.setShuffleMode(false)
    }
    fun subscribeCurrentTrackIndex(): Flow<Int> {
        return playerDataSource.subscribeCurrentTrackIndex()
    }



}