package com.yes.core.di

import android.content.Context
import androidx.room.Room
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.data.dataSource.MediaDataStore
import com.yes.core.repository.data.dataSource.SettingsDataStore
import com.yes.core.repository.dataSource.PlayListDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(
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
        return Room.databaseBuilder(
            context,
            PlayListDataBase::class.java,
            "your_database_name"
        ).build()
        /* return Room.inMemoryDatabaseBuilder(
             context,
             PlayListDataBase::class.java,
         ).build()*/
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