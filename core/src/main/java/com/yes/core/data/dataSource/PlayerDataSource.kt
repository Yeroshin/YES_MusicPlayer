package com.yes.core.data.dataSource

import android.content.ComponentName
import android.content.Context
import android.widget.Toast
import androidx.media3.common.C.TIME_UNSET
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.yes.core.data.entity.PlayerStateDataSourceEntity
import com.yes.core.presentation.MusicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



class PlayerDataSource(
    private val context: Context,
) {
    interface MediaControllerListener {
        fun onMediaControllerReady(controller: MediaController)
    }
    private val commandQueue: MutableList<QueuedCommand> = mutableListOf()
    sealed class QueuedCommand{
        data object Play:QueuedCommand()
        data class SetTracks(val items: List<MediaItem>):QueuedCommand()
    }
    private var mediaControllerListener: MediaControllerListener? = null
    fun setMediaControllerListener(listener: MediaControllerListener) {
        mediaControllerListener = listener
    }
    private val sessionToken = SessionToken(
        context,
        ComponentName(
            context,
            MusicService::class.java
        )
    )
    private val  controllerFuture by lazy {
            MediaController.Builder(
                context,
                sessionToken
            ).buildAsync()
    }
    private val controller: MediaController by lazy {
        controllerFuture.get()
    }


    init {
        initializeController()
    }



    private fun initializeController() {
        val done=controllerFuture.isDone
        controllerFuture.addListener(
            {
                val done=controllerFuture.isDone
                val controller = controllerFuture.get()
                println("controller get")
                mediaControllerListener?.onMediaControllerReady(controller)
                setController()
                commandQueue.forEach { queuedCommand ->
                    when (queuedCommand){
                        is QueuedCommand.Play -> controller.play()
                        is QueuedCommand.SetTracks -> controller.setMediaItems(queuedCommand.items)
                    }


                }
                // Очистить очередь
                commandQueue.clear()
            },
            MoreExecutors.directExecutor()
        )
      //  val controller = controllerFuture.get()
        println("controller waiting")
        //////////////////


    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _mediaMetadataFlow: MutableStateFlow<PlayerStateDataSourceEntity> =
        MutableStateFlow(
            PlayerStateDataSourceEntity()
        )
    private val mediaMetadataFlow: StateFlow<PlayerStateDataSourceEntity> = _mediaMetadataFlow
    private val _mediaSessionIdFlow=MutableStateFlow(0)
    private val mediaSessionIdFlow: StateFlow<Int> = _mediaSessionIdFlow

    private fun setController() {
        controller.addListener(
            object : Player.Listener {

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    if (controller.duration != TIME_UNSET) {
                        _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                            duration = controller.duration
                        )
                    }
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                        }

                        Player.STATE_BUFFERING -> {
                            _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                                stateBuffering = true
                            )
                        }

                        Player.STATE_READY -> {
                            _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                                duration = controller.duration
                            )
                        }

                        Player.STATE_ENDED -> {

                        }
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _isPlaying.value = isPlaying
                }

                override fun onPlayerError(error: PlaybackException) {
                    Toast.makeText(context, "player error", Toast.LENGTH_SHORT).show()
                }

                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                        mediaMetadata = mediaMetadata
                    )
                }


            }
        )
    }
    fun setRepeatMode(mode:Int){
        controller.repeatMode=mode
    }
    fun getRepeatMode():Int{
        return controller.repeatMode
    }
    fun setShuffleMode(mode:Boolean){
        controller.shuffleModeEnabled=mode
    }
    fun getShuffleMode():Boolean{
        return controller.shuffleModeEnabled
    }

    fun seekToPrevious() {
        controller.seekToPreviousMediaItem()
    }

    fun seekToNext() {
        controller.seekToNextMediaItem()
    }

    fun getDuration(): Long {
        return controller.duration
    }

    fun play() {
        if (controllerFuture.isDone) {
            val controller = controllerFuture.get()
            controller.play()
        } else {
            // Добавить команду приостановки в очередь
            commandQueue.add(QueuedCommand.Play)
        }
       // controller.play()
    }

    fun seek(position: Long) {
        controller.seekTo(position)
    }

    fun pause() {
        controller.pause()
    }

    fun getCurrentPosition(): Long {
        return controller.currentPosition
    }

    fun setTracks(items: List<MediaItem>) {
        if (controllerFuture.isDone) {
            val controller = controllerFuture.get()
            controller.setMediaItems(items)
        } else {
            // Добавить команду приостановки в очередь
            commandQueue.add(QueuedCommand.SetTracks(items))
        }
        controller.setMediaItems(items)
    }

    fun subscribeCurrentPlayerData(): Flow<PlayerStateDataSourceEntity> {
        return mediaMetadataFlow
    }

    fun subscribeAudioSessionId():Flow<Int> {
        return mediaSessionIdFlow
    }

}