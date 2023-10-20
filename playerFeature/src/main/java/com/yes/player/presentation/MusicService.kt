package com.yes.player.presentation

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.yes.core.domain.models.DomainResult
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.player.presentation.mapper.Mapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MusicService: MediaSessionService() {
    interface DependencyResolver{
        fun getMusicServiceComponent(): Dependency
    }
    private  lateinit var dependency:Dependency
  /*  private val dependency by lazy {
        (application as  DependencyResolver)
            .getMusicServiceComponent()
    }*/
    private lateinit var mapper: Mapper
    private lateinit var subscribeCurrentPlaylistTracksUseCase:SubscribeCurrentPlaylistTracksUseCase

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private val mediaSession: MediaSession by lazy {
        MediaSession.Builder(this, player).build()
    }
    private val player by lazy {
        ExoPlayer.Builder(this).build()
    }
    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession


    override fun onCreate() {
        super.onCreate()
        dependency=(application as DependencyResolver).getMusicServiceComponent()
        mapper=dependency.mapper
        subscribeCurrentPlaylistTracksUseCase=dependency.subscribeCurrentPlaylistTracksUseCase
        subscribeTracks()
    }

    private fun subscribeTracks() {

        serviceScope.launch {

            val playLists = subscribeCurrentPlaylistTracksUseCase()

            when (playLists) {
                is DomainResult.Success -> {
                    playLists.data.collect {
                        it.map { item ->
                            player.addMediaItem(
                                mapper.map(item)
                            )
                        }

                    }
                }

                is DomainResult.Error -> TODO()
            }
        }
    }
    class Dependency(
        val mapper: Mapper,
        val subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase
    )
}