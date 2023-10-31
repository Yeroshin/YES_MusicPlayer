package com.yes.trackdialogfeature.di.module

import android.content.Context
import androidx.room.Room
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.data.dataSource.MediaDataStore
import com.yes.core.data.dataSource.SettingsSharedPreferences
import com.yes.core.data.dataSource.PlayListDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
    ): PlayListDataBase {
        return Room.inMemoryDatabaseBuilder(
            context,
            PlayListDataBase::class.java,
        ).build()
    }
    @Singleton
    @Provides
    fun providesPlayListDao(
        dataBase: PlayListDataBase
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
    fun providesMediaDataStore(
        context: Context
    ): MediaDataStore {
        return MediaDataStore(
            context
        )
    }

}