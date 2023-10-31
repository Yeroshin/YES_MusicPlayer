package com.yes.core.di.module

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.yes.core.presentation.MusicService
import dagger.Module
import dagger.Provides

@Module
class MusicServiceModule (
   private val context: Context
){
    @Provides
    fun providesContext(): Context {
        return context
    }
    @Provides
    fun providesExoPlayer(
        context: Context
    ): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }
    @Provides
    fun providesMediaSession(
        context: Context,
        player: ExoPlayer
    ): MediaSession {
        return MediaSession.Builder(context, player).build()
    }
    @Provides
    fun providesDependency(
        mediaSession: MediaSession
    ): MusicService.Dependency {
        return MusicService.Dependency(
            mediaSession
        )
    }
}