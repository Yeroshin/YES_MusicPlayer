package com.yes.trackdialogfeature.di.component

import com.yes.core.di.component.DataComponent
import com.yes.trackdialogfeature.di.TrackDialogScope
import com.yes.trackdialogfeature.di.module.TrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import dagger.Component

@Component(
    dependencies = [DataComponent::class],
    modules = [
        TrackDialogModule::class,
      //  DataModule::class
    ]
)
@TrackDialogScope
interface TrackDialogComponent {
    fun getDependency(): TrackDialog.Dependency
}