package com.yes.playlistfeature.di.component

import com.yes.core.di.component.CoreComponent
import com.yes.playlistfeature.di.PlaylistScope
import com.yes.playlistfeature.di.module.PlaylistModule
import com.yes.playlistfeature.presentation.ui.Playlist
import dagger.Component

@Component(
    dependencies=[CoreComponent::class],
    modules = [
        PlaylistModule::class,
    ]
)
@PlaylistScope
interface PlaylistComponent {
    fun getDependency(): Playlist.Dependency
}