package com.example.musicplayerfeature.media

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.media.MediaBrowserServiceCompat


private const val MY_MEDIA_ROOT_ID = "media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
class MusicService: MediaBrowserServiceCompat() {
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(MY_MEDIA_ROOT_ID, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID == parentId) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems.toMutableList())
    }
    private var mediaSession: MediaSessionCompat? = null
    private lateinit var stateBuilder: PlaybackStateCompat.Builder
    override fun onCreate() {
        super.onCreate()
        // Create a MediaSessionCompat
        mediaSession = MediaSessionCompat(baseContext, "MusicService").apply {

            // Enable callbacks from MediaButtons and TransportControls
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY
                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(stateBuilder.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
           // setCallback(MySessionCallback())

            // Set the session's token so that client activities can communicate with it.
            setSessionToken(sessionToken)
        }
    }
}