package com.yes.core.repository.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.entity.PlayListEntity
import com.yes.core.repository.entity.TrackEntity


@Database(entities = [PlayListEntity::class, TrackEntity::class], version = 1)
abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): IPlayListDao

}