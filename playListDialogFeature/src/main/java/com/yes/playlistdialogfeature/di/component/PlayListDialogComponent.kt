package com.yes.playlistdialogfeature.di.component

import com.yes.core.di.component.CoreComponent
import com.yes.playlistdialogfeature.di.PlayListDialogScope
import com.yes.playlistdialogfeature.di.module.PlayListDialogModule
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import dagger.Component

@Component(
    dependencies=[CoreComponent::class],
    modules = [
        PlayListDialogModule::class,
    ]
)
@PlayListDialogScope
interface PlayListDialogComponent {
    fun getDependency(): PlayListDialog.Dependency
}