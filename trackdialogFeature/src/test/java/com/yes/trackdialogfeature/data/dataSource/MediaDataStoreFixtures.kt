package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.SharedFixtureGenerator
import com.example.shared_test.UiFixtures
import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.core.repository.entity.TrackEntity
import com.yes.core.repository.entity.MediaDataStoreEntity
import kotlin.random.Random

object MediaDataStoreFixtures {

    private val selectedItem = UiFixtures.getSelectedArtistIndex()
    private val albums =
        SharedFixtureGenerator.getAlbumsNames().map {
           MediaDataStoreEntity(
                it
            )
        }
    private val artists =
        SharedFixtureGenerator.getArtistsNames().map {
            MediaDataStoreEntity(
                it
            )
        }
    private val tracks =
        SharedFixtureGenerator.getTracksNames().map {
            MediaDataStoreEntity(
                it
            )
        }


    fun getArtists(): List<MediaDataStoreEntity> {
        return artists
    }

    fun getTracksFromSelectedArtist(): List<MediaDataStoreEntity> {
        return tracksAudio.filter {
            it.artist == artists[selectedItem].title
        }
    }

    fun getTracksMedia(): List<MediaDataStoreEntity> {
        return tracks
    }

    private val tracksAudio = tracks.mapIndexed { index, element ->
        element.copy(
            element.title,
            artists[selectedItem].title,
            albums[index].title,
            Random.nextLong(135000, 270000),
            "/storage/emulated/0/media/${element.title}.mp3",
            Random.nextLong(4000000, 8000000)
        )
    }

    fun getTracksAudio(): List<MediaDataStoreEntity> {
        return tracksAudio
    }

    private val mediaRepositoryMapper = MediaRepositoryMapper()
    fun getSelectedTracksAudio(): List<TrackEntity> {
        return listOf(
            mediaRepositoryMapper.mapToTrack(
                tracksAudio[selectedItem]
            )
        )
    }
}