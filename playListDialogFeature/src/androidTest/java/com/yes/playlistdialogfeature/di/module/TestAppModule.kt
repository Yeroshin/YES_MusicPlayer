package com.yes.playlistdialogfeature.di.module

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.dataSource.MediaDataStore
import com.yes.core.data.dataSource.SettingsSharedPreferences
import com.yes.core.data.dataSource.YESDataBase
import dagger.Module
import dagger.Provides

import javax.inject.Singleton
private const val USER_PREFERENCES = "user_preferences"
@Module
class TestAppModule(
    private val context: Context
) {
    @Provides
    fun providesActivity(): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesDatabase(
        context: Context
    ): YESDataBase {
        return Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            YESDataBase::class.java,
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT OR IGNORE INTO playlists(name) VALUES('default hello world 1');")
                }
            }
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesPlayListDao(
        dataBase: YESDataBase
    ): IPlayListDao {
        return dataBase.playListDao()
    }


    @Provides
    fun providesSettingsDataStore(
        context: Context
    ): SettingsSharedPreferences {
        return SettingsSharedPreferences(
            context
        )
    }
    @Provides
    @Singleton
    fun provideDataStore( context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
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

}