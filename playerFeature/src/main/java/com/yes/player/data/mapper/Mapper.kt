package com.yes.player.data.mapper

import com.yes.core.repository.entity.TrackEntity
import com.yes.player.domain.model.Track

class Mapper {
    fun map(track: TrackEntity): Track {
        return Track(
            track.id?:0,
            track.id?:0,
            track.artist,
            track.title,
            track.uri,
            track.duration,
            track.album,
            track.size,
            track.position
        )
    }
    fun map(track:Track): TrackEntity {
        return TrackEntity(
            track.id,
            track.id,
            track.artist,
            track.title,
            track.uri,
            track.duration,
            track.album,
            track.size,
            track.position
        )
    }
}