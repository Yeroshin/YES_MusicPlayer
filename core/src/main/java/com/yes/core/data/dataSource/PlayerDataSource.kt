package com.yes.core.data.dataSource

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.media3.common.C.TIME_UNSET
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import com.yes.core.data.entity.PlayerStateDataSourceEntity
import com.yes.core.presentation.MusicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.ExecutionException


class PlayerDataSource(
    private val context: Context,
) {
    interface MediaControllerListener {
        fun onMediaControllerReady(controller: MediaController)
    }

    private val commandQueue: MutableList<QueuedCommand> = mutableListOf()

    sealed class QueuedCommand {
        data object Play : QueuedCommand()
        data class SetTracks(val items: List<MediaItem>) : QueuedCommand()
    }


    private val sessionToken by lazy {
        SessionToken(
            context,
            ComponentName(
                context,
                MusicService::class.java
            )
        )
    }
    private val controllerFuture by lazy {
        MediaController.Builder(
            context.applicationContext,
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
        //val done = controllerFuture.isDone
        controllerFuture.addListener(
            {

                val done = controllerFuture.isDone
                try {
                    val controller = controllerFuture.get()
                    println("controller GET!!!")
                    setController()
                    commandQueue.forEach { queuedCommand ->
                        when (queuedCommand) {
                            is QueuedCommand.Play -> controller.play()
                            is QueuedCommand.SetTracks -> controller.setMediaItems(queuedCommand.items)
                        }
                    }
                    // Очистить очередь
                    commandQueue.clear()
                } catch (e: ExecutionException) {
                    println("controller: The session rejected the connection")

                    if (e.cause is SecurityException) {
                        println("controller: The session rejected the connection")
                    }
                }
            },
            MoreExecutors.directExecutor()
            // context.mainExecutor
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

    private val _mediaSessionIdFlow = MutableStateFlow(0)
    private val mediaSessionIdFlow: StateFlow<Int> = _mediaSessionIdFlow

    private val _currentTrackIndexFlow = MutableStateFlow(-1)
    private val currentTrackIndexFlow: StateFlow<Int> = _currentTrackIndexFlow


    private fun getMediaItems(): List<MediaItem> {
        val mediaItems = mutableListOf<MediaItem>()
        for (index in 0..controller.mediaItemCount) {
            mediaItems.add(
                controller.getMediaItemAt(index)
            )
        }
        return mediaItems
    }

    private fun setController() {
        controller.addListener(
            object : Player.Listener {

                override fun onTracksChanged(tracks: Tracks) {
                    super.onTracksChanged(tracks)

                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    if (controller.duration != TIME_UNSET) {
                        _mediaMetadataFlow.value = _mediaMetadataFlow.value.copy(
                            duration = controller.duration
                        )
                    }
                }


                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                        }

                        Player.STATE_BUFFERING -> {
                            _mediaMetadataFlow.value = _mediaMetadataFlow.value.copy(
                                stateBuffering = true
                            )
                        }

                        Player.STATE_READY -> {
                            _mediaMetadataFlow.value = _mediaMetadataFlow.value.copy(
                                duration = controller.duration,
                                stateBuffering = false
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
                    _currentTrackIndexFlow.value = controller.currentMediaItemIndex
                    _mediaMetadataFlow.value = _mediaMetadataFlow.value.copy(
                        mediaMetadata = mediaMetadata
                    )
                }


            }
        )
    }

    fun setRepeatMode(mode: Int) {
        controller.repeatMode = mode
    }

    fun getRepeatMode(): Int {
        return controller.repeatMode
    }

    fun setShuffleMode(mode: Boolean) {
        controller.shuffleModeEnabled = mode
    }

    fun getShuffleMode(): Boolean {
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

    fun seekToItemAndPlay(index: Int) {
        controller.seekTo(3, 0)
    }

    fun play(index: Int) {
        if (controllerFuture.isDone) {
            val controller = controllerFuture.get()
            controller.seekTo(index, TIME_UNSET)
            controller.play()

        } else {
            // Добавить команду приостановки в очередь
            commandQueue.add(QueuedCommand.Play)
        }
    }

    fun play() {
        if (controllerFuture.isDone) {
            val controller = controllerFuture.get()
            controller.play()
        } else {
            commandQueue.add(QueuedCommand.Play)
        }
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

    fun setTracks(items: List<MediaItem>, index: Int) {

        /*   if (controllerFuture.isDone) {
               val controller = controllerFuture.get()
               controller.setMediaItems(items)
               controller.seekTo(index, TIME_UNSET)
           } else {
               // Добавить команду приостановки в очередь
               commandQueue.add(QueuedCommand.SetTracks(items))
           }*/
        //   controller.setMediaItems(items)
    }

    fun subscribeCurrentPlayerData(): Flow<PlayerStateDataSourceEntity> {
        return mediaMetadataFlow
    }

    fun subscribeAudioSessionId(): Flow<Int> {
        return mediaSessionIdFlow
    }

    fun subscribeCurrentTrackIndex(): Flow<Int> {
        return currentTrackIndexFlow
    }


}