package com.yes.core.repository.dataSource

import android.content.ComponentName
import android.content.Context
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.yes.core.presentation.MusicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerDataSource(
    private val context: Context
) {
    private lateinit var controllerFuture: ListenableFuture<androidx.media3.session.MediaController>
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
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean>
        get() = _isPlaying
    private fun setController() {
        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    Toast.makeText(context, "MediaItemTransition", Toast.LENGTH_SHORT).show()
                }

                override fun onTracksChanged(tracks: Tracks) {
                    Toast.makeText(context, "TracksChanged", Toast.LENGTH_SHORT).show()
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    // Обработка изменений состояния проигрывания
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                            Toast.makeText(context, "idle", Toast.LENGTH_SHORT).show()
                        }

                        Player.STATE_BUFFERING -> {
                            Toast.makeText(context, "buffering", Toast.LENGTH_SHORT).show()
                        }

                        Player.STATE_READY -> {
                            _isPlaying.value = true
                            Toast.makeText(context, "ready", Toast.LENGTH_SHORT).show()
                        }

                        Player.STATE_ENDED -> {
                            Toast.makeText(context, "ended", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
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

    fun isPlaying(): Flow<Boolean> {
        return isPlaying
    }

    fun setTracks(items: List<MediaItem>) {
        controller.addMediaItems(items)
    }
    fun clearTracks(){
        controller.clearMediaItems()
    }


}