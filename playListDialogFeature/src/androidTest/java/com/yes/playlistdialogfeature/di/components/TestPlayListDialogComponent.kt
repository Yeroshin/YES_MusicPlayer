package com.yes.playlistdialogfeature.di.components

import androidx.lifecycle.ViewModelProvider
import com.yes.playlistdialogfeature.di.module.TestAppModule
import com.yes.playlistdialogfeature.di.module.TestPlayListDialogModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestPlayListDialogModule::class, TestAppModule::class])
interface TestPlayListDialogComponent {
    fun getViewModelFactory(): ViewModelProvider.Factory

}