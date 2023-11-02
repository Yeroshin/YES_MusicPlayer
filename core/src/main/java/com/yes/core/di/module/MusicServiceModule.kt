package com.yes.core.di.module

import android.content.Context
import android.media.AudioManager
import android.media.audiofx.Visualizer
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.yes.core.di.MusicServiceScope
import com.yes.core.presentation.MusicService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MusicServiceModule (
){


    @Provides
    fun providesMediaSession(
        context: Context,
        player: ExoPlayer
    ): MediaSession {
        return MediaSession.Builder(context, player).build()
    }

    @Provides
    fun providesVisualizer(
        audioSessionId: Int
    ): Visualizer {
        return Visualizer(audioSessionId)
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    fun  providesExoPlayer(
        context: Context,
        audioSessionId:Int
    ): ExoPlayer {
        val player=ExoPlayer.Builder(context).build()
        player.audioSessionId=audioSessionId
        return player
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