package com.yes.playlistfeature.di.component

import com.yes.core.di.component.CoreComponent
import com.yes.core.di.module.DataModule
import com.yes.playlistfeature.di.PlaylistScope
import com.yes.playlistfeature.di.module.PlaylistModule
import com.yes.playlistfeature.presentation.ui.Playlist
import dagger.Component

@Component(
    modules = [
        PlaylistModule::class,
        DataModule::class
    ]
)
@PlaylistScope
interface PlaylistComponent {
    fun getDependency(): Playlist.Dependency
}