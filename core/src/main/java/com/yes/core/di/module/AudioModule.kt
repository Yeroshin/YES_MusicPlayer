package com.yes.core.di.module

import android.content.Context
import android.media.AudioManager
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.yes.core.data.factory.RendererFactory
import com.yes.core.di.AudioScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AudioModule {
  /*  @AudioScope
    @Provides
    fun providesAudioSessionId(
        context: Context
    ): Int{
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.generateAudioSessionId()
    }*/
  @OptIn(UnstableApi::class)
  @Provides
  @AudioScope
  fun providesAudioSessionId(
      player: ExoPlayer
  ): Int{
      return player.audioSessionId
  }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    @AudioScope
    fun  providesExoPlayer(
        context: Context,
       // audioSessionId:Int
    ): ExoPlayer {
        val player=  ExoPlayer.Builder(context).build()
       // player.audioSessionId=audioSessionId
        val t=player.audioSessionId
        return player
    }
}