package com.yes.trackdialogfeature.data.repository.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity
import com.yes.trackdialogfeature.domain.entity.Track


@Database(entities = [PlayListEntity::class, Track::class], version = 1)
abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): IPlayListDao
}