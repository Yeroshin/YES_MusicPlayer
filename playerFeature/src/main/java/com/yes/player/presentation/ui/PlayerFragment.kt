package com.yes.player.presentation.ui

import android.content.ComponentName
import android.content.Context
import android.media.browse.MediaBrowser
import android.media.session.MediaController
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.musicplayerfeature.media.MusicService
import com.yes.player.R
import com.yes.player.databinding.PlayerBinding


class PlayerFragment : Fragment(R.layout.player) {
    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as PlayerBinding
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayerBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)
        binder.btnPlay.setOnClickListener {
            loadItem()
        }
        //////////////////////////

        //////////////////////////
        return binder.root
    }

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(requireContext())
    private fun loadItem() {

        val mediaBrowser = MediaBrowser(
            context, ComponentName(
                requireContext(),
                MusicService::class.java
            ), mediaBrowserConnectionCallback, null
        )
        mediaBrowser.connect()
        val mediaController = MediaController(requireContext(), mediaBrowser.sessionToken)
        mediaController.
    }

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
}