package com.yes.playlistfeature.data.mapper

import com.yes.core.repository.entity.TrackEntity
import com.yes.playlistfeature.domain.entity.Track
import com.yes.playlistfeature.presentation.model.TrackUI

class Mapper {
    fun map(track:TrackEntity):Track{
        return Track(
            track.id?:0
        )
    }

}