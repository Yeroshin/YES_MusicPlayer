package com.yes.trackdialogfeature.data.repository.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yes.trackdialogfeature.data.repository.entity.PlayListDao
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity


@Database(entities = [PlayListEntity::class], version = 1)
abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): PlayListDao
}