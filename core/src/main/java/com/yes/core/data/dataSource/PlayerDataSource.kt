package com.yes.core.data.dataSource

import android.content.ComponentName
import android.content.Context
import android.media.audiofx.Visualizer
import android.widget.Toast
import androidx.media3.common.C.TIME_UNSET
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.yes.core.data.entity.PlayerStateDataSourceEntity
import com.yes.core.data.factory.VisualizerFactory
import com.yes.core.presentation.MusicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class PlayerDataSource(
    private val context: Context,
    private val visualizerFactory: VisualizerFactory
) {
    private val  controllerFuture by lazy {
            MediaController.Builder(
                context,
                sessionToken
            )
                .buildAsync()
    }
    private val controller by lazy {
        controllerFuture.get()
    }
       // get() = controllerFuture.get()
    private val sessionToken = SessionToken(
        context,
        ComponentName(
            context,
            MusicService::class.java
        )
    )
    init {
        initializeController()
    }



    private fun initializeController() {

        controllerFuture.addListener(
            {
                setController(controller)
            },
            MoreExecutors.directExecutor()
        )
        //////////////////


    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _mediaMetadataFlow: MutableStateFlow<PlayerStateDataSourceEntity> =
        MutableStateFlow(
            PlayerStateDataSourceEntity()
        )
    private val mediaMetadataFlow: StateFlow<PlayerStateDataSourceEntity> = _mediaMetadataFlow


    private fun setController(controller: MediaController) {
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
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }

                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    _mediaMetadataFlow.value = PlayerStateDataSourceEntity(
                        mediaMetadata = mediaMetadata
                    )
                }

                @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
                override fun onAudioSessionIdChanged(audioSessionId: Int) {
                    visualizerFactory.createVisualizer(audioSessionId).setDataCaptureListener(
                        object : Visualizer.OnDataCaptureListener {
                            override fun onWaveFormDataCapture(
                                visualizer: Visualizer?,
                                waveform: ByteArray?,
                                samplingRate: Int
                            ) {

                            }

                            override fun onFftDataCapture(
                                visualizer: Visualizer?,
                                fft: ByteArray?,
                                samplingRate: Int
                            ) {

                            }
                        },
                        Visualizer.getMaxCaptureRate() / 2, true, false
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

    fun getDuration(): Long {
        return controller.duration
    }

    fun play() {
        controller.play()
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
        controller.setMediaItems(items)
    }

    fun subscribeCurrentPlayerData(): Flow<PlayerStateDataSourceEntity> {
        return mediaMetadataFlow
    }

    fun getAudioSessionId() {
        controller.connectedToken
    }

}