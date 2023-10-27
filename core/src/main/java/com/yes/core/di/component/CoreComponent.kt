package com.yes.core.di.component

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.yes.core.di.module.DataModule
import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.dataSource.PlayListDataBase
import com.yes.core.repository.dataSource.PlayerDataSource
import com.yes.core.repository.dataSource.SettingsDataStore
import dagger.Component
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(

    modules = [
        DataModule::class,
    ]
)
interface CoreComponent {
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher
    fun providesPlayerDataSource(): PlayerDataSource
    fun providesPlayListDao(): IPlayListDao
    fun providesSettingsDataStore(): SettingsDataStore
}