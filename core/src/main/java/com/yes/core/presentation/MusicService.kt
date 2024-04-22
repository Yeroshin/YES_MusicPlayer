package com.yes.core.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.yes.core.data.entity.PlayerStateDataSourceEntity
import com.yes.core.data.mapper.Mapper
import com.yes.core.di.component.MusicServiceComponent
import com.yes.core.domain.entity.Track
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.GetCurrentTrackIndexUseCase
import com.yes.core.domain.useCase.SetSettingsTrackIndexUseCase
import com.yes.core.domain.useCase.SubscribeCurrentPlaylistTracksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MusicService : MediaSessionService() {
    interface DependencyResolver {
        fun getMusicServiceComponent(context: Context): MusicServiceComponent
    }

    data class Dependency(
        val mediaSession: MediaSession,
        val mapper: Mapper,
        val subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
        val getCurrentTrackIndexUseCase: GetCurrentTrackIndexUseCase,
        val setSettingsTrackIndexUseCase: SetSettingsTrackIndexUseCase
    )

    private val dependency by lazy {
        (application as DependencyResolver)
            .getMusicServiceComponent(this).getDependency()
    }

    private val mediaSession by lazy {
        dependency.mediaSession
    }
    private val serviceScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate() {
        super.onCreate()
        //  mediaSession =dependency.mediaSession
        mediaSession.player.addListener(
            object : Player.Listener {

                override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                    // val currentMediaItemIndex=mediaSession?.player?.currentMediaItemIndex
                    serviceScope.launch {
                        val result = dependency.setSettingsTrackIndexUseCase(
                            SetSettingsTrackIndexUseCase.Params(
                                mediaSession.player.currentMediaItemIndex
                            )
                        )
                        when (result) {
                            is DomainResult.Success -> {}
                            is DomainResult.Error -> {}
                        }
                    }

                }


            }
        )
        serviceScope.launch {
            val playLists = dependency.subscribeCurrentPlaylistTracksUseCase()
            val currentTrackIndex = dependency.getCurrentTrackIndexUseCase()
            when (playLists) {
                is DomainResult.Success -> {
                    when (currentTrackIndex) {
                        is DomainResult.Success -> {
                            combine(
                                playLists.data,
                                currentTrackIndex.data
                            ) { playList:List<Track>, trackIndex:Int ->
                                    mediaSession.player.setMediaItems(
                                        playList.map { track ->
                                            dependency.mapper.mapToMediaItem(track)
                                        }
                                    )
                                    if (playList.isNotEmpty() || trackIndex != -1) {
                                        mediaSession.player.seekTo(trackIndex, 0)
                                    }
                            }.collect{}

                        }

                        is DomainResult.Error -> {}
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    override fun onGetSession(

        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession {
        println("MusicService onGetSession")
        Log.d("alarm", "MusicService!")
        return mediaSession
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {

            "PLAY_MUSIC" -> {
                // Здесь запускаем воспроизведение музыки
            }
            // Другие обработки команд
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession.player!!
        if (!player.playWhenReady || player.mediaItemCount == 0) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf()
        }
    }

    override fun onDestroy() {
        mediaSession.run {
            player.release()
            release()
            // mediaSession = null
        }
        super.onDestroy()
    }
}