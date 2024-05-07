package com.yes.core.di.module

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.audio.TeeAudioProcessor
import com.yes.core.data.factory.RendererFactory
import com.yes.core.di.AudioScope
import com.yes.core.presentation.ui.tmp.AudioProcessor
import com.yes.core.presentation.ui.tmp.RawDataAudioProcessor
import dagger.Module
import dagger.Provides
import java.nio.ByteBuffer
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

   /* @OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    @AudioScope
    fun  providesExoPlayer(
        context: Context,
       // audioSessionId:Int
    ): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }*/

    @OptIn(UnstableApi::class) @Provides
    @AudioScope
    fun providesAudioProcessor(): AudioProcessor {
        return  AudioProcessor()
    }
    @OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    @AudioScope
    fun  providesExoPlayer(
        context: Context,
        audioProcessor: AudioProcessor
    ): ExoPlayer {
       val rendererFactory = RawDataAudioProcessor(context, audioProcessor)

       return ExoPlayer.Builder(context)
           .setRenderersFactory(rendererFactory)
           .build()
    }

}