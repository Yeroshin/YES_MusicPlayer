package com.yes.player.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.musicplayerfeature.media.MusicService
import com.yes.player.R
import com.yes.player.databinding.PlayerBinding
import java.util.UUID


class PlayerFragment : Fragment() {
    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as PlayerBinding
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayerBinding.inflate(inflater, container, false)
      //  super.onCreateView(inflater, container, savedInstanceState)
        binder.btnPlay.setOnClickListener {
            loadItem()
        }
        //////////////////////////

        //////////////////////////
        return binder.root
    }

    override fun onStart() {
        super.onStart()
        mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(requireContext())
        mediaBrowser = MediaBrowserCompat(
            context, ComponentName(
                requireContext(),
                MusicService::class.java
            ), mediaBrowserConnectionCallback, null
        )
        mediaBrowser.connect()
    }
    var playlistId = UUID.randomUUID().toString()
    private lateinit var mediaBrowserConnectionCallback:MediaBrowserConnectionCallback
    private lateinit var mediaBrowser : MediaBrowserCompat
    private fun loadItem() {


       // mediaBrowser.connect()

        val mediaController = MediaControllerCompat(requireContext(), mediaBrowser.sessionToken)
        val mediaItem = MediaSessionCompat.QueueItem(mediaDescription, mediaMetadata.description.mediaId.hashCode().toLong())
       mediaController.addQueueItem(mediaItem)
    }
    private var subscriptionCallback: MediaBrowserCompat.SubscriptionCallback =
        object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: List<MediaBrowserCompat.MediaItem>
            ) {
                val a=parentId
                val b=children
                // Обработка изменений в плейлисте
            }
        }
    private lateinit var mediaController: MediaControllerCompat
    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            //  playbackState.postValue(state ?: EMPTY_PLAYBACK_STATE)
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
    }
    private inner class MediaBrowserConnectionCallback(private val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            // Get a MediaController for the MediaSession.
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
            mediaBrowser.subscribe(playlistId, subscriptionCallback);
            //  isConnected.postValue(true)
        }

        override fun onConnectionSuspended() {
            //  isConnected.postValue(false)
        }

        override fun onConnectionFailed() {
            //  isConnected.postValue(false)
        }
    }

}