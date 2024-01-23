package com.yes.musicplayer.equalizer.di.components

import com.yes.core.di.component.AudioSessionIdComponent
import com.yes.core.di.component.CoreComponent
import com.yes.musicplayer.equalizer.di.EqualizerScope
import com.yes.musicplayer.equalizer.di.module.EqualizerModule
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import dagger.Component

@Component(
    dependencies = [
        CoreComponent::class,
    ],
    modules = [
        EqualizerModule::class,
    ]
)
@EqualizerScope
interface EqualizerComponent {
    fun getDependency(): EqualizerScreen.Dependency
}