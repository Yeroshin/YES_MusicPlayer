package com.yes.player.data.repository

import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.player.data.mapper.Mapper
import com.yes.player.domain.model.PlayerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class PlayerRepository(
    private val playerDataSource: PlayerDataSource,
    private val mapper: Mapper
) {
    fun play() {
        playerDataSource.play()
    }

    fun pause() {
        playerDataSource.pause()
    }

    fun seekForward() {
        playerDataSource.seekToNext()
    }

    fun seekPrevious() {
        playerDataSource.seekToPrevious()
    }

    fun seek(position: Long) {
        playerDataSource.seek(position)
    }

    fun isPlaying(): Boolean {
        return playerDataSource.isPlaying.value
    }

    suspend fun subscribeCurrentPosition() = flow {

        playerDataSource.isPlaying.collect {
            while (it) {
                emit(
                    mapper.map(
                        playerDataSource.getCurrentPosition()
                    )
                )
                delay(1000)
            }
        }

    }

    fun subscribeCurrentTrackInfo(): Flow<PlayerState> {
        return playerDataSource.subscribeCurrentPlayerData().map {
            mapper.map(it)
        }
    }

    fun subscribeAudioSessionId(): Flow<Int> {
        return playerDataSource.subscribeAudioSessionId()
    }
}