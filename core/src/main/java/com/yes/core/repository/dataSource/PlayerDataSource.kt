package com.yes.core.repository.dataSource

import android.content.ComponentName
import android.content.Context
import android.widget.Toast
import androidx.media3.common.C.TIME_UNSET
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.yes.core.presentation.MusicService
import com.yes.core.repository.entity.PlayerStateDataSourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class PlayerDataSource(
    private val context: Context
) {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController
        get() = controllerFuture.get()

    init {
        initializeController()
    }

    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                context,
                SessionToken(
                    context,
                    ComponentName(
                        context,
                        MusicService::class.java
                    )
                )
            )
                .buildAsync()
        controllerFuture.addListener(
            { setController(controllerFuture.get()) },
            MoreExecutors.directExecutor()
        )
        //////////////////


    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean>
        get() = _isPlaying

    private val _mediaMetadataFlow: MutableStateFlow<PlayerStateDataSourceEntity> =
        MutableStateFlow(
            PlayerStateDataSourceEntity()
        )
    private val mediaMetadataFlow: StateFlow<PlayerStateDataSourceEntity> = _mediaMetadataFlow


    private fun setController(controller: MediaController) {
        controller.addListener(
            object : Player.Listener {

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    // Toast.makeText(context, "MediaItemTransition", Toast.LENGTH_SHORT).show()
                   if(controller.duration!=TIME_UNSET){
                       _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                           duration = controller.duration
                       )
                   }
                }

                override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                    super.onTimelineChanged(timeline, reason)
                }

                override fun onTracksChanged(tracks: Tracks) {
                    val tmp = controller.duration

                    Toast.makeText(context, "TracksChanged", Toast.LENGTH_SHORT).show()
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
                            Toast.makeText(context, "ended", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _isPlaying.value = isPlaying
                }

                override fun onPlayerError(error: PlaybackException) {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }

                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                        mediaMetadata = mediaMetadata
                    )
                }
            }
        )
    }

    fun seekToPrevious() {
        controller.seekToPreviousMediaItem()
    }

    fun seekToNext() {
        controller.seekToNextMediaItem()
    }

    fun play() {
        controller.play()
    }

    fun pause() {
        controller.pause()
    }

    fun getCurrentPosition(): Long {
        return controller.currentPosition
    }


    fun setTracks(items: List<MediaItem>) {
        controller.setMediaItems(items)
    }


    fun subscribeCurrentPlayerData(): Flow<PlayerStateDataSourceEntity> {
        return mediaMetadataFlow
    }


}