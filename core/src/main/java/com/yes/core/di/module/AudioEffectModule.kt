package com.yes.core.di.module

import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.yes.core.data.repository.EqualizerRepositoryImpl
import com.yes.core.di.AudioScope
import com.yes.core.di.MusicServiceScope
import dagger.Module
import dagger.Provides
@Module
class AudioEffectModule {
   /* @OptIn(UnstableApi::class)
    @Provides
    fun providesEqualizerFactory(
        player: ExoPlayer
    ): EqualizerRepositoryImpl.Factory {
        return EqualizerRepositoryImpl.Factory(player.audioSessionId)
    }*/
    @OptIn(UnstableApi::class)
    @Provides
    fun providesEqualizer(
        player: ExoPlayer
    ): Equalizer {
        return Equalizer(0, player.audioSessionId)
    }
    @OptIn(UnstableApi::class)
    @Provides
    @AudioScope
    fun providesLoudnessEnhancer(
        player: ExoPlayer
    ): LoudnessEnhancer {
        return LoudnessEnhancer(
            player.audioSessionId
        )
    }
}