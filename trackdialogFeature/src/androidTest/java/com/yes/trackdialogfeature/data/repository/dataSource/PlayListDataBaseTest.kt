package com.yes.trackdialogfeature.data.repository.dataSource

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.yes.core.domain.repository.IPlayListDao
import com.yes.musicplayer.data.entity.PlayListEntity
import com.yes.core.domain.models.Track
import junit.framework.TestCase.assertEquals

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PlayListDataBaseTest {
    private lateinit var userDao: IPlayListDao
    private lateinit var db: com.yes.musicplayer.data.dataSource.PlayListDataBase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            com.yes.musicplayer.data.dataSource.PlayListDataBase::class.java
        ).build()
        userDao = db.playListDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadPlayList() {
        val playlist = com.yes.musicplayer.data.entity.PlayListEntity(
            null,
            "Default",
            0,
            0
        )
        userDao.savePlaylist(playlist)
        val retrievedPlaylist = userDao.getPlaylist(playlist.name).copy(id = null)
        assertEquals(playlist, retrievedPlaylist)
    }

    @Test
    fun writeAndReadTracks() {

        val playlist = com.yes.musicplayer.data.entity.PlayListEntity(
            null,
            "Default",
            0,
            0
        )
        val tracks = listOf(
            Track(
                null,
                playlist.name,
                "Dire Straits",
                "Money for Nothing",
                "uri",
                654256,
                "Brothers in Arms",
                843567
            ),
        )
        userDao.saveTracks(tracks)
        val retrievedTracks = userDao.getTracks(playlist.name).map { it.copy(id = null) }
        assertEquals(playlist, retrievedTracks)
    }
}