package com.yes.core.di.component

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.yes.core.di.module.DataModule
import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.data.dataSource.MediaDataStore
import com.yes.core.data.dataSource.PlayListDataBase
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.data.factory.RendererFactory
import com.yes.core.util.EspressoIdlingResource
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
    ]
)
interface CoreComponent {
    fun providesEspressoIdlingResource(): EspressoIdlingResource?
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher
    @MainDispatcher
    fun providesMainDispatcher(): CoroutineDispatcher
    fun providesActivity(): Context
    fun providesDatabase(): PlayListDataBase
    fun providesPlayListDao(): IPlayListDao
  //  fun providesSettingsSharedPreferences(): SettingsSharedPreferences
    fun provideDataStore(): DataStore<Preferences>
    fun providesSettingsDataStore(): SettingsDataStore
    fun providesPlayerDataSource(): PlayerDataSource
    fun providesMediaDataStore(): MediaDataStore
    fun providesRendererFactory(): RendererFactory
}