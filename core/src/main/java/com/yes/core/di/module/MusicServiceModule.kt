package com.yes.core.di.module

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.yes.core.presentation.MusicService
import dagger.Module
import dagger.Provides

@Module
class MusicServiceModule {


    @Provides
    fun providesMediaSession(
        context: Context,
        player: ExoPlayer
    ): MediaSession {
        return MediaSession.Builder(context, player).build()
    }

  /*  @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    fun  providesPlayer(
        context: Context,
        audioSessionId:Int
    ): ExoPlayer {
        val player=ExoPlayer.Builder(context).build()
        player.audioSessionId=audioSessionId
        return player
    }*/


  /* @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
   @Provides
   fun  providesExoPlayer(
       context: Context,
       rendererFactory:RendererFactory
   ): ExoPlayer {
       return  ExoPlayer.Builder(context)
          .setRenderersFactory(rendererFactory)
           .build()
   }*/
    @Provides
    fun providesDependency(
        mediaSession: MediaSession
    ): MusicService.Dependency {
        return MusicService.Dependency(
            mediaSession
        )
    }
}