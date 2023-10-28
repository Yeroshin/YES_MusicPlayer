package com.yes.playlistdialogfeature.di.component

import com.yes.core.di.module.DataModule
import com.yes.playlistdialogfeature.di.PlayListDialogScope
import com.yes.playlistdialogfeature.di.module.PlaylistDialogModule
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import dagger.Component

@Component(
    modules = [
        PlaylistDialogModule::class,
        DataModule::class
    ]
)
@PlayListDialogScope
interface PlayListDialogComponent {
    fun getDependency(): PlayListDialog.Dependency
}