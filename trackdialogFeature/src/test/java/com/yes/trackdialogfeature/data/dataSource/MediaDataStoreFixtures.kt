package com.yes.trackdialogfeature.data.dataSource

import com.example.shared_test.SharedFixtureGenerator
import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.Track
import kotlin.random.Random

object MediaDataStoreFixtures {
    private const val count = 5
    private val selectedItem = SharedFixtureGenerator.getSelectedItem()
    private val albums =
        SharedFixtureGenerator.generateMediaItemNames(count).map {
            MediaDataStoreEntity(
                it
            )
        }
    private val artists =
        SharedFixtureGenerator.generateMediaItemNames(count).map {
            MediaDataStoreEntity(
                it
            )
        }
    private val tracks =
        SharedFixtureGenerator.generateMediaItemNames(count).map {
            MediaDataStoreEntity(
                it
            )
        }


    fun getCount(): Int {
        return count
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

    val mediaRepositoryMapper = MediaRepositoryMapper()
    fun getSelectedTracksAudio(): List<Track> {
        return listOf(
            mediaRepositoryMapper.mapToTrack(
                tracksAudio[selectedItem]
            )
        )
    }
}