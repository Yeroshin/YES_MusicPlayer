package com.yes.trackdialogfeature.di.module

import android.content.Context
import androidx.room.Room
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.dataSource.MediaDataStore
import com.yes.core.data.dataSource.SettingsSharedPreferences
import com.yes.core.data.dataSource.YESDataBase
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
    ): YESDataBase {
        return Room.inMemoryDatabaseBuilder(
            context,
            YESDataBase::class.java,
        ).build()
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
    fun providesMediaDataStore(
        context: Context
    ): MediaDataStore {
        return MediaDataStore(
            context
        )
    }

}