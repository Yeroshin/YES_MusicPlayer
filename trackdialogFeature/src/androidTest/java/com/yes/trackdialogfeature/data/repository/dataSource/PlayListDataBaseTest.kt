package com.yes.trackdialogfeature.data.repository.dataSource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.yes.trackdialogfeature.data.repository.entity.PlayListDao
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity
import com.yes.trackdialogfeature.data.repository.entity.TrackEntity
import junit.framework.TestCase.assertEquals

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PlayListDataBaseTest {
    private lateinit var userDao: PlayListDao
    private lateinit var db: PlayListDataBase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlayListDataBase::class.java
        ).build()
        userDao = db.playListDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadPlayList() {
        val playlist = PlayListEntity(
            null,
            "Default",
            0,
            0
        )
        /* val track = TrackEntity(
             null,
             "1",
             "Dire Straits",
             "Money for Nothing",
             "uri",
              1000
         )*/
        userDao.savePlaylist(playlist)
        val retrievedPlaylist = userDao.getPlaylist(playlist.name).copy(id = null)
        assertEquals(playlist, retrievedPlaylist)
    }

    @Test
    fun writeAndReadTracks() {

        val playlist = PlayListEntity(
            null,
            "Default",
            0,
            0
        )
        val tracks = listOf(
            TrackEntity(
                null,
                playlist.name,
                "Dire Straits",
                "Money for Nothing",
                "uri",
                1000
            ),
        )
        userDao.saveTracks(tracks)
        val retrievedPlaylist = userDao.getTracks(playlist.name).map { it.copy(id = null) }
        assertEquals(playlist, retrievedPlaylist)
    }
}