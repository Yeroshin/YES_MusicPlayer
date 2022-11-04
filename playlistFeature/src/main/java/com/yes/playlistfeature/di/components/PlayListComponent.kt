package com.yes.playlistfeature.di.components

import com.yes.playlistfeature.di.module.PlayListModule
import com.yes.playlistfeature.presentation.PlaylistFragment
import dagger.Component

@Component(modules=[PlayListModule::class])
interface PlayListComponent {
    fun inject(fragment:PlaylistFragment)
}