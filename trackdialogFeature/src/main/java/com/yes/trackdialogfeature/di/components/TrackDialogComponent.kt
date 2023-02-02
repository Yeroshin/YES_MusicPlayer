package com.yes.trackdialogfeature.di.components

import com.yes.trackdialogfeature.di.module.TrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import dagger.Component

@Component(modules = [TrackDialogModule::class])
interface TrackDialogComponent {
    fun inject(dialog: TrackDialog)
}