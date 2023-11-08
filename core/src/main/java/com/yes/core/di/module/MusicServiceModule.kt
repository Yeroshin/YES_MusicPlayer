package com.yes.core.di.module

import android.content.Context
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.audio.TeeAudioProcessor
import androidx.media3.session.MediaSession
import com.yes.core.data.factory.RendererFactory
import com.yes.core.presentation.MusicService
import dagger.Module
import dagger.Provides
import java.nio.ByteBuffer

@Module
class MusicServiceModule {


    @Provides
    fun providesMediaSession(
        context: Context,
        player: ExoPlayer
    ): MediaSession {
        return MediaSession.Builder(context, player).build()
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    fun providesTeeAudioProcessorAudioBufferSink(
    ): TeeAudioProcessor.AudioBufferSink {
        return object : TeeAudioProcessor.AudioBufferSink {
            override fun flush(sampleRateHz: Int, channelCount: Int, encoding: Int) {
                Log.d(": ", "waveformbytearray is not null.");

            }

            override fun handleBuffer(buffer: ByteBuffer) {
              //  _byteBuffer.value=buffer.array()
                Log.d(": ", "waveformbytearray is not null.");

            }

        }
    }
    @Provides
    fun providesRendererFactory(
        context: Context,
        audioBufferSink : TeeAudioProcessor.AudioBufferSink
    ): RendererFactory {
        return RendererFactory(context,audioBufferSink)
    }

   @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
   @Provides
   fun  providesExoPlayer(
       context: Context,
       rendererFactory:RendererFactory
   ): ExoPlayer {
       return  ExoPlayer.Builder(context)
          .setRenderersFactory(rendererFactory)
           .build()
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