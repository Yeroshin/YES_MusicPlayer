package com.yes.core.di.module

import android.content.Context
import android.media.AudioManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AudioSessionIdModule {
    @Singleton
    @Provides
    fun providesAudioSessionId(
        context: Context
    ): Int{
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.generateAudioSessionId()
    }
}