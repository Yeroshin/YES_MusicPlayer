package com.yes.playlistdialogfeature.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.data.dataSource.MediaDataStore
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.core.repository.dataSource.PlayListDataBase
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
            context.applicationContext,
            PlayListDataBase::class.java,
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