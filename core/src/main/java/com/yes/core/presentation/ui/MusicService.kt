package com.yes.core.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.yes.core.data.mapper.Mapper
import com.yes.core.di.component.MusicServiceComponent
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.GetCurrentTrackIndexUseCase
import com.yes.core.domain.useCase.InitEqualizerUseCase
import com.yes.core.domain.useCase.SetSettingsTrackIndexUseCase
import com.yes.core.domain.useCase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.core.presentation.ui.tmp.AudioProcessor
import com.yes.speechmanagerfeature.data.Speech
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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
        val setSettingsTrackIndexUseCase: SetSettingsTrackIndexUseCase,
        val initEqualizerUseCase: InitEqualizerUseCase,
        val audioProcessor: AudioProcessor
    )

    private val dependency by lazy {
        (application as DependencyResolver)
            .getMusicServiceComponent(this).getDependency()
    }

    private val mediaSession by lazy {
        dependency.mediaSession
    }
    private val serviceScope = CoroutineScope(Dispatchers.Main)
    private val playerListener by lazy {
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
    }
    private val frequencies = intArrayOf(60000, 230000, 910000, 3000000, 14000000)
    @OptIn(UnstableApi::class) @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate() {
        super.onCreate()
        //  mediaSession =dependency.mediaSession
        mediaSession.player.addListener(
            playerListener
        )
        serviceScope.launch {
            var loaded=false
            dependency.initEqualizerUseCase(
                InitEqualizerUseCase.Params(
                    frequencies=frequencies
                )
            )
            val playLists = dependency.subscribeCurrentPlaylistTracksUseCase()
            val currentTrackIndex = dependency.getCurrentTrackIndexUseCase()
            when (playLists) {
                is DomainResult.Success -> {
                    when (currentTrackIndex) {
                        is DomainResult.Success -> {
                            playLists.data.flatMapLatest {playList ->
                                mediaSession.player.setMediaItems(
                                    playList.map { track ->
                                        dependency.mapper.mapToMediaItem(track)
                                    }
                                )

                                if (playList.isNotEmpty()){
                                    currentTrackIndex.data
                                }else{
                                    flow {
                                        emit(-1)
                                    }
                                }
                            }.collect{trackIndex ->
                                if (trackIndex != -1&&!loaded) {
                                    mediaSession.player.seekTo(trackIndex, 0)
                                }
                                loaded=true
                            }

                        }

                        is DomainResult.Error -> {}
                    }
                }

                is DomainResult.Error -> {}
            }
        }
        /////////////////////////
        ////speech

        val speech= Speech(this)
       /* dependency.audioProcessor.setListener {byteBuffer->
            val s=byteBuffer.capacity()
            println()
        }*/

        }

    override fun onGetSession(

        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession {
        println("MusicService onGetSession")
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
        val player = mediaSession.player
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