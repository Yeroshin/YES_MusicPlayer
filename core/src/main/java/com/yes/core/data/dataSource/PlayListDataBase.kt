package com.yes.core.data.dataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.entity.PlayListDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Database(
    entities = [PlayListDataBaseEntity::class, PlayListDataBaseTrackEntity::class],
    version = 1
)
abstract class PlayListDataBase : RoomDatabase() {

    abstract fun playListDao(): IPlayListDao

    companion object {

        private var INSTANCE: PlayListDataBase? = null
        fun getInstance(context: Context): PlayListDataBase {
            return INSTANCE ?: run {
                Room.databaseBuilder(
                    context,
                    PlayListDataBase::class.java,
                    "my_database"
                )
//TODO write an article about the right way to prepopulate database
                    .build().also {

                        val applicationScope = CoroutineScope(Job())
                        applicationScope.launch(Dispatchers.IO) {
                            val playListDao = it.playListDao()
                            val count = playListDao.getPlaylistCount()
                            if (count == 0) {
                                playListDao.savePlaylist(
                                    PlayListDataBaseEntity(
                                        null,
                                        "default playlist"
                                    )
                                )
                            }
                            INSTANCE = it
                        }
                    }
            }
        }
    }
}