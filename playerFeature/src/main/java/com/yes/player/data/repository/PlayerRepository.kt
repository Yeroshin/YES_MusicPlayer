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



    suspend fun subscribeCurrentPosition()=flow {
        while (true) {
            emit(
                DurationCounter(
                    playerDataSource.getCurrentPosition()
                )
            )
            delay(1000)
        }
       /* playerDataSource.isPlaying.collect{
            while (it) {
                emit(
                    DurationCounter(
                        playerDataSource.getCurrentPosition()
                    )
                )
                delay(1000)
            }
        }*/

    }

}