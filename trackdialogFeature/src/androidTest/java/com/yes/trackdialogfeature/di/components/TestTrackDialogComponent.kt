package com.yes.trackdialogfeature.di.components

import androidx.lifecycle.ViewModelProvider
import com.yes.trackdialogfeature.data.repository.dataSource.PlayListDataBase
import com.yes.trackdialogfeature.data.repository.dataSource.SettingsDataStore
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
import dagger.Component



@Component(modules = [TestTrackDialogModule::class,TestAppModule::class])
interface TestTrackDialogComponent {
    fun getViewModelFactory(): ViewModelProvider.Factory
  //  fun inject(dialog: TrackDialogEndToEndTest.MyActivity)
    fun getDataBase(): IPlayListDao
    fun getSettings(): ISettingsRepository
}