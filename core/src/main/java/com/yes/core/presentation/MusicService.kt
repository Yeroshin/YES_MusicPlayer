package com.yes.core.presentation

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.yes.core.di.component.MusicServiceComponent
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MusicService : MediaSessionService() {
    interface DependencyResolver {
        fun getMusicServiceComponent(context: Context): MusicServiceComponent
    }
    data class Dependency(
        val mediaSession: MediaSession
    )
    private val component by lazy {
        (application as DependencyResolver)
            .getMusicServiceComponent(this)
    }
    private val dependency by lazy {
        component.getDependency()
    }

    private val mediaSession: MediaSession by lazy {
        dependency.mediaSession
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {

            "PLAY_MUSIC" -> {
                // Здесь запускаем воспроизведение музыки
            }
            // Другие обработки команд
        }
        return super.onStartCommand(intent, flags, startId)
    }
}