package com.yes.playlistfeature.data.mapper


import com.yes.core.repository.entity.TrackEntity
import com.yes.playlistfeature.domain.entity.Track
import androidx.media3.common.MediaItem




class Mapper {
    fun map(track:TrackEntity):Track{
        return Track(
            track.id?:0,
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
    fun map(track:Track):TrackEntity{
        return TrackEntity(
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
    fun mapToMediaItem(item:Track): MediaItem {
       /* val mmd = MediaMetadata.Builder()
            .setTitle("Example")
            .setArtist("Artist name")
            .setMediaUri("...".toUri())
            .build()*/

        return MediaItem.Builder()
            .setUri(item.uri)
                .build()
    }

}