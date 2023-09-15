package com.yes.trackdialogfeature.di.module

import android.content.Context
import androidx.room.Room
import com.yes.musicplayer.data.dataSource.MediaDataStore
import com.yes.musicplayer.data.dataSource.PlayListDataBase
import com.yes.musicplayer.data.dataSource.SettingsDataStore
import com.yes.core.domain.repository.IPlayListDao
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
    ): com.yes.musicplayer.data.dataSource.PlayListDataBase {
        return Room.inMemoryDatabaseBuilder(
            context,
            com.yes.musicplayer.data.dataSource.PlayListDataBase::class.java,
        ).build()
    }
    @Singleton
    @Provides
    fun providesPlayListDao(
        dataBase: com.yes.musicplayer.data.dataSource.PlayListDataBase
    ): IPlayListDao {
        return dataBase.playListDao()
    }


    @Provides
    fun providesSettingsDataStore(
        context: Context
    ): com.yes.musicplayer.data.dataSource.SettingsDataStore {
        return com.yes.musicplayer.data.dataSource.SettingsDataStore(
            context
        )
    }


    @Provides
    fun providesMediaDataStore(
        context: Context
    ): com.yes.musicplayer.data.dataSource.MediaDataStore {
        return com.yes.musicplayer.data.dataSource.MediaDataStore(
            context
        )
    }

}