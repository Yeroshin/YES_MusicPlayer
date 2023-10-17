package com.yes.trackdialogfeature.di.components

import androidx.lifecycle.ViewModelProvider
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.core.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.SettingsRepository
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [TestTrackDialogModule::class,TestAppModule::class])
interface TestTrackDialogComponent {
    fun getViewModelFactory(): ViewModelProvider.Factory
  //  fun inject(dialog: TrackDialogEndToEndTest.MyActivity)
    fun getDataBase(): IPlayListDao
    fun getSettings(): SettingsRepository
}