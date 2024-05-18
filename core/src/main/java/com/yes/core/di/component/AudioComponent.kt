package com.yes.core.di.component

import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import androidx.media3.exoplayer.ExoPlayer
import com.yes.core.data.repository.EqualizerRepositoryImpl
import com.yes.core.di.AudioScope
import com.yes.core.di.module.AudioEffectModule
import com.yes.core.di.module.AudioModule
import com.yes.core.presentation.ui.tmp.AudioProcessor
import dagger.Component

@AudioScope
@Component(
    dependencies = [
        DataComponent::class,
    ],
    modules = [
        AudioModule::class,
        AudioEffectModule::class
    ]
)
interface AudioComponent {
    fun getAudioSessionId():Int
    fun getExoPlayer(): ExoPlayer
    fun getEqualizer(): Equalizer
    fun getLoudnessEnhancer(): LoudnessEnhancer
}