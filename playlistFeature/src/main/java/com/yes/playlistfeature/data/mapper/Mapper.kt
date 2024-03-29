package com.yes.playlistfeature.data.mapper


import android.net.Uri
import android.provider.MediaStore
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.playlistfeature.domain.entity.Track
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import java.io.File


class Mapper {
    fun map(track: PlayListDataBaseTrackEntity): Track {
        return Track(
            track.id ?: 0,
            track.playlistId,
            track.artist,
            track.title,
            track.uri,
            track.duration,
            track.album,
            track.size,
            track.position
        )
    }

    fun map(track: Track): PlayListDataBaseTrackEntity {
        return PlayListDataBaseTrackEntity(
            track.id,
            track.playlistId,
            track.artist,
            track.title,
            track.uri,
            track.duration,
            track.album,
            track.size,
            track.position
        )
    }

    fun mapToMediaItem(item: Track): MediaItem {
         val mediaMetadata = MediaMetadata.Builder()
             .setAlbumTitle(item.album)
             .setArtist(item.artist)
             .setTitle(item.title)
             .build()
        return MediaItem.Builder()
            .setUri(item.uri)
            .setMediaMetadata(mediaMetadata)
            .build()
     /*   return MediaItem.Builder()
            .setUri("https://www.mediacollege.com/audio/tone/files/10kHz_44100Hz_16bit_05sec.mp3")
            .setMediaMetadata(mediaMetadata)
            .build()*/
    }

}