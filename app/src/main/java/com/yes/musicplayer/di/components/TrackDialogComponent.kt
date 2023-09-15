package com.yes.musicplayer.di.components

import com.yes.core.di.DataModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.di.module.TrackDialogModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TrackDialogModule::class, DataModule::class])
interface TrackDialogComponent {
    fun getTrackDialogDependency(): TrackDialog.Dependency
}