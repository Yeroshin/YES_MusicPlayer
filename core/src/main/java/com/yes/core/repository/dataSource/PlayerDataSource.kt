package com.yes.core.repository.dataSource

import android.content.ComponentName
import android.content.Context
import android.media.MediaMetadataRetriever
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.MetadataRetriever
import androidx.media3.exoplayer.source.TrackGroupArray
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import com.yes.core.presentation.MusicService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


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
        controllerFuture.addListener({ setController(controllerFuture.get()) }, MoreExecutors.directExecutor())
        //////////////////


    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean>
        get() = _isPlaying

    private val _mediaMetadataFlow: MutableStateFlow<MediaMetadata> = MutableStateFlow(
        MediaMetadata.Builder()
          /*  .setDisplayTitle("Your title here")
            .setArtist("Your artist name")*/
            .build()
    )
    private val mediaMetadataFlow = _mediaMetadataFlow.asStateFlow()

    private val listener=object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
         //   Toast.makeText(context, "MediaItemTransition", Toast.LENGTH_SHORT).show()
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

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            //TODO
            _mediaMetadataFlow.value=mediaMetadata
            Toast.makeText(context, "MediaMetadataChanged", Toast.LENGTH_SHORT).show()
        }

        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onPlaylistMetadataChanged(mediaMetadata)
            Toast.makeText(context, "MediaMetadataChanged", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setController(controller: MediaController) {
        controller.addListener(
            listener
        )
    }

    fun seekToPrevious() {
        controller.seekToPreviousMediaItem()
    }

    fun seekToNext() {
        controller.seekToNextMediaItem()
    }

    fun play() {
       // controller.prepare()
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
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun  setTracks(items: List<MediaItem>) {

        controller.setMediaItems(items)
        controller.removeMediaItem(0)
      /*  controller.prepare()
        controller.seekToNext()
        controller.seekToNextMediaItem()*/
       // controller.seekToDefaultPosition(10)
       // controller.seekToDefaultPosition(0)
      /*  controller.seekTo(5,0)
        controller.play()
        controller.pause()*/
      /* controller.seekTo(0,50000)
        controller.play()
        controller.pause()
        controller.seekTo(0,0)*/
    }

    fun subscribeCurrentMediaMetadata(): Flow<MediaMetadata> {
        return mediaMetadataFlow
    }


}