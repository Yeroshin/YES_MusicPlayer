package com.yes.alarmclockfeature.data.repository


import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import com.yes.alarmclockfeature.data.mapper.Mapper
import com.yes.alarmclockfeature.domain.model.Track
import com.yes.core.data.dataSource.PlayerDataSource



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
    fun play() {
        playerDataSource.play()
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


}