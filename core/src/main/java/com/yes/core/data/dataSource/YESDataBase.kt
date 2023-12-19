package com.yes.core.data.dataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yes.core.data.entity.AlarmDataBaseEntity
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.entity.PlayListDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.domain.repository.IAlarmDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Database(
    entities = [
        PlayListDataBaseEntity::class,
        PlayListDataBaseTrackEntity::class,
        AlarmDataBaseEntity::class
    ],
    version = 1
)
abstract class YESDataBase : RoomDatabase() {
    abstract fun alarmDao():IAlarmDao
    abstract fun playListDao(): IPlayListDao

    companion object {

        private var INSTANCE: YESDataBase? = null
        fun getInstance(context: Context): YESDataBase {
            return INSTANCE ?: run {
                Room.databaseBuilder(
                    context,
                    YESDataBase::class.java,
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