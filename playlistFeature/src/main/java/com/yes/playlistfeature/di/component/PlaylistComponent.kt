package com.yes.playlistfeature.di.component

import com.yes.core.di.component.CoreComponent
import com.yes.playlistfeature.di.PlaylistScope
import com.yes.playlistfeature.di.module.PlaylistModule
import com.yes.playlistfeature.presentation.ui.PlaylistScreen
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [
        PlaylistModule::class,
     //   DataModule::class
    ]
)
@PlaylistScope
interface PlaylistComponent {
    fun getDependency(): PlaylistScreen.Dependency
}