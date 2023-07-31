package com.yes.trackdialogfeature.di.components

import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import dagger.Component

@Component(modules = [TestTrackDialogModule::class])
interface TestTrackDialogComponent {
    fun inject(dialog: TrackDialog)
}