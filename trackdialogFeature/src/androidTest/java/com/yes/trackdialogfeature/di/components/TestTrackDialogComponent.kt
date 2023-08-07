package com.yes.trackdialogfeature.di.components

import androidx.lifecycle.ViewModelProvider
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.presentation.TrackDialogEndToEndTest
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import dagger.Component
import javax.inject.Singleton


@Component(modules = [TestTrackDialogModule::class,TestAppModule::class])
interface TestTrackDialogComponent {
    fun getViewModelFactory(): ViewModelProvider.Factory
  //  fun inject(dialog: TrackDialogEndToEndTest.MyActivity)
}