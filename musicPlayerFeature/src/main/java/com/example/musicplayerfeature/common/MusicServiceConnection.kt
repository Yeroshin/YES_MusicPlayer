package com.example.musicplayerfeature.common

import android.content.ComponentName
import android.content.Context


class MusicServiceConnection(context: Context, serviceComponent: ComponentName) {
 /*   val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls
    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)
    private val mediaBrowser = MediaBrowserCompat(
        context,
        serviceComponent,
        mediaBrowserConnectionCallback,
        null // optional Bundle
    ).apply { connect() }
    private lateinit var mediaController: MediaControllerCompat
    private inner class MediaBrowserConnectionCallback(private val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            // Get a MediaController for the MediaSession.
             mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }

          //  isConnected.postValue(true)
        }
        override fun onConnectionSuspended() {
          //  isConnected.postValue(false)
        }
        override fun onConnectionFailed() {
          //  isConnected.postValue(false)
        }
    }

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }
    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            // playbackState.postValue(state ?: EMPTY_PLAYBACK_STATE)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            // When ExoPlayer stops we will receive a callback with "empty" metadata. This is a
            // metadata object which has been instantiated with default values. The default value
            // for media ID is null so we assume that if this value is null we are not playing
            // anything.
            /* nowPlaying.postValue(
                if (metadata?.id == null) {
                    NOTHING_PLAYING
                } else {
                    metadata
                }
            )*/
        }
    }*/


}