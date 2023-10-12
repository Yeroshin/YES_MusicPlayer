package com.yes.playlistfeature.data.mapper

import com.yes.core.repository.entity.TrackEntity
import com.yes.playlistfeature.domain.entity.Track

class Mapper {
    fun map(track:TrackEntity):Track{
        return Track(
            track.id?:0
        )
    }
}