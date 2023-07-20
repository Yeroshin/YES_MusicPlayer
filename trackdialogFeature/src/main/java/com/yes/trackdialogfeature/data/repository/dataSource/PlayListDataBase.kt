package com.yes.trackdialogfeature.data.repository.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yes.trackdialogfeature.data.repository.PlayListDao
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity
import com.yes.trackdialogfeature.data.repository.entity.TrackEntity


@Database(entities = [PlayListEntity::class,TrackEntity::class], version = 1)
abstract class PlayListDataBase : RoomDatabase() {
    abstract fun playListDao(): PlayListDao
}