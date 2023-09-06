package com.yes.trackdialogfeature.di.module

import android.content.Context
import androidx.room.Room
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.PlayListDataBase
import com.yes.trackdialogfeature.data.repository.dataSource.SettingsDataStore
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
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
    ): SettingsDataStore {
        return SettingsDataStore(
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