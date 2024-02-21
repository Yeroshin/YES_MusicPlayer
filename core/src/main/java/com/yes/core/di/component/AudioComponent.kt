package com.yes.core.di.component

import androidx.media3.exoplayer.ExoPlayer
import com.yes.core.di.AudioScope
import com.yes.core.di.module.AudioModule
import com.yes.core.di.module.DataModule
import dagger.Component
import javax.inject.Singleton

@AudioScope
@Component(
    dependencies = [
        DataComponent::class,
    ],
    modules = [
        AudioModule::class,
    ]
)
interface AudioComponent {
    fun getAudioSessionId():Int
    fun getExoPlayer(): ExoPlayer
}