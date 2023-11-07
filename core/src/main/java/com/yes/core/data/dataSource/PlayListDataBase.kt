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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Database(
    entities = [PlayListDataBaseEntity::class, PlayListDataBaseTrackEntity::class],
    version = 1
)
/*abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): IPlayListDao

}*/
abstract class PlayListDataBase : RoomDatabase() {

    abstract fun playListDao(): IPlayListDao

    companion object {

        @Volatile
        private var INSTANCE: PlayListDataBase? = null
        private val callback = object : Callback() {
            private val applicationScope = CoroutineScope(Job())
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // db.execSQL("INSERT INTO play_list_table (name, description, date_created) VALUES ('default playlist', 'Default playlist description', '2022-01-01');")
                applicationScope.launch(Dispatchers.IO) {
                    val inst = INSTANCE
                    inst?.playListDao()?.let {
                        val count = it.getPlaylistCount()
                        if (count == 0) {
                            // Создаем первоначальную запись
                            it.savePlaylist(
                                PlayListDataBaseEntity(
                                    null,
                                    // context.getString(R.string.error_message)
                                    "default playlist"
                                )
                            )
                        }
                    }
                        ?: 0
                }

                /*  val myDao = getInstance(context).playListDao()
                  val count = myDao.getPlaylistCount()
                  if (count == 0) {
                      // Создаем первоначальную запись
                      myDao.savePlaylist(
                          PlayListDataBaseEntity(
                              null,
                             // context.getString(R.string.error_message)
                              "default playlist"
                          )
                      )
                  }*/
            }
        }

        fun getInstance(context: Context): PlayListDataBase {
            return INSTANCE
                ?: synchronized(this) {
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
                                val defaultPlaylist = PlayListDataBaseEntity(
                                    null,
                                    "default playlist",
                                )
                                playListDao.savePlaylist(defaultPlaylist)
                                INSTANCE = it
                            }
                        }
                }
        }
    }
}