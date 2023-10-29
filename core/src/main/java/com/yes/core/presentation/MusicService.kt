package com.yes.core.presentation

import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MusicService: MediaSessionService() {
    private val player by lazy {
        ExoPlayer.Builder(this).build()
    }
    private val mediaSession: MediaSession by lazy {
        MediaSession.Builder(this, player).build()
    }
    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession

}