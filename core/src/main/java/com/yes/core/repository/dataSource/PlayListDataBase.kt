package com.yes.core.repository.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.domain.models.PlayList
import com.yes.core.domain.models.Track



@Database(entities = [PlayList::class, Track::class], version = 1)
abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): IPlayListDao
}