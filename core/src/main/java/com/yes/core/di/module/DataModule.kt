package com.yes.core.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.data.dataSource.MediaDataStore
import com.yes.core.data.dataSource.PlayListDataBase
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.data.factory.RendererFactory
import com.yes.core.util.EspressoIdlingResource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
private const val USER_PREFERENCES = "user_preferences"
@Module
class DataModule(
    private val context: Context
) {
    @Provides
    fun providesEspressoIdlingResource(): EspressoIdlingResource? {
        return null
    }
    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    @Provides
    fun providesActivity(): Context {
        return context
    }

    @Provides
    fun providesDatabase(
        context: Context
    ): PlayListDataBase {
        return PlayListDataBase .getInstance(context)
    }
    @Provides
    fun providesPlayListDao(
        dataBase: PlayListDataBase
    ): IPlayListDao {
        return dataBase.playListDao()
    }
  /*PlaylistModule  @Provides
    fun providesSettingsSharedPreferences(
        context: Context
    ): SettingsSharedPreferences {
        return SettingsSharedPreferences(
            context
        )
    }*/
    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {

        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
         //   migrations = listOf(SharedPreferencesMigration(context,USER_PREFERENCES)),
           // scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )

    }
    @Provides
    fun providesSettingsDataStore(
        dataStore: DataStore<Preferences>
    ): SettingsDataStore {
        return SettingsDataStore(
            dataStore
        )
    }
    @Provides
    fun providesPlayerDataSource(
        context: Context,
    ): PlayerDataSource {
        return PlayerDataSource(
            context,
        )
    }



    @Provides
    fun providesMediaDataStore(
        context: Context
    ): MediaDataStore {
        return MediaDataStore(
            context
        )
    }
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    @Singleton
    fun providesRendererFactory(
        context: Context
    ): RendererFactory {
        return RendererFactory(context)
    }

}
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher