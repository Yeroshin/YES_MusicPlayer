package com.yes.player.presentation.ui

import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.viewbinding.ViewBinding
import com.google.common.util.concurrent.ListenableFuture

import com.google.common.util.concurrent.MoreExecutors
import com.yes.player.databinding.PlayerBinding
import com.yes.player.presentation.MusicService


class PlayerFragment : Fragment() {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null


    override fun onStart() {
        super.onStart()
        initializeController()

    }
    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                requireContext(),
                SessionToken(
                    requireContext(),
                    ComponentName(requireContext(),
                        MusicService::class.java
                    )
                )
            )
                .buildAsync()
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }
    private fun setController() {
        val controller = this.controller ?: return
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
            play()
        }
        binder.btnRew.setOnClickListener {
            seekToPrevious()
        }
        binder.btnFwd.setOnClickListener {
            next()
        }
        return binder.root
    }
    private fun seekToPrevious(){
        controller?.seekToPreviousMediaItem()
    }
    fun next(){
        controller?.seekToNextMediaItem()
    }

    private fun play() {
        controller?.play()
    }

}