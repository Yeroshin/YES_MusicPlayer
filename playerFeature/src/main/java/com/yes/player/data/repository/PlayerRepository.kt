package com.yes.player.data.repository

import com.yes.core.repository.dataSource.PlayerDataSource
import com.yes.player.domain.model.DurationCounter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow


class PlayerRepository(
    private val playerDataSource: PlayerDataSource
) {
    fun play(){
        playerDataSource.play()
    }
    fun pause(){
        playerDataSource.pause()
    }
    fun seekForward(){
        playerDataSource.seekToNext()
    }
    fun seekPrevious(){
        playerDataSource.seekToPrevious()
    }


    fun isPlaying():Boolean{
        return playerDataSource.isPlaying()
    }
    suspend fun subscribeCurrentPosition()=flow {
        while (playerDataSource.isPlaying()) {

            emit(
                DurationCounter(
                    playerDataSource.getCurrentPosition()
                )
            )
            // Задержка на 1 секунду
            delay(1000)
        }
    }

}