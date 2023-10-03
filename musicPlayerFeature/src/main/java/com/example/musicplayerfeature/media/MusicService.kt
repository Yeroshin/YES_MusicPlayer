package com.example.musicplayerfeature.media



import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture


private const val MY_MEDIA_ROOT_ID = "media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
@UnstableApi class MusicService: MediaSessionService(), MediaSession.Callback {
    private var mediaSession: MediaSession? = null

    // If desired, validate the controller before returning the media session
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): androidx.media3.session.MediaSession? =
        mediaSession

    // Create your player and media session in the onCreate lifecycle event
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: MutableList<MediaItem>
    ): ListenableFuture<MutableList<MediaItem>> {
        val updatedMediaItems = mediaItems
            .map {
                it.buildUpon().setUri(it.mediaId)
                .build()
            }.toMutableList()
        return Futures.immediateFuture(updatedMediaItems)
    }
    override fun onPlaybackResumption(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo
    ): ListenableFuture<MediaSession.MediaItemsWithStartPosition> {
        val settable = SettableFuture.create<MediaSession.MediaItemsWithStartPosition>()
        /*scope.launch {
            // Your app is responsible for storing the playlist and the start position
            // to use here
            val resumptionPlaylist = restorePlaylist()
            settable.set(resumptionPlaylist)
        }*/
        return settable
    }

}