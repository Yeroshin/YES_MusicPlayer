package com.yes.musicplayer.equalizer.di.components

import com.yes.core.di.component.AudioComponent
import com.yes.core.di.component.DataComponent
import com.yes.core.presentation.ui.BaseDependency
import com.yes.musicplayer.equalizer.di.EqualizerScope
import com.yes.musicplayer.equalizer.di.module.EqualizerModule
import dagger.Component

@Component(
    dependencies = [
        DataComponent::class,
        AudioComponent::class
    ],
    modules = [
        EqualizerModule::class,
    ]
)
@EqualizerScope
interface EqualizerComponent {
    fun getDependency(): BaseDependency
}