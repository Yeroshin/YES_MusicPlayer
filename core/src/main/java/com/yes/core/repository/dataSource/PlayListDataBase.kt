package com.yes.core.repository.dataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.entity.PlayListDataBaseEntity
import com.yes.core.repository.entity.PlayListDataBaseTrackEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(entities = [PlayListDataBaseEntity::class, PlayListDataBaseTrackEntity::class], version = 1)
/*abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): IPlayListDao

}*/
abstract class PlayListDataBase  : RoomDatabase() {

    abstract fun playListDao(): IPlayListDao

    companion object {

        @Volatile
        private var INSTANCE: PlayListDataBase ? = null

        fun getInstance(context: Context): PlayListDataBase  {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayListDataBase ::class.java,
                    "my_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            GlobalScope.launch {
                                val myDao = PlayListDataBase .getInstance(context).playListDao()
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
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}