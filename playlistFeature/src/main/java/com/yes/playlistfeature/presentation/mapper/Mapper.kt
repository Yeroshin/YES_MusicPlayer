package com.yes.playlistfeature.presentation.mapper

import com.yes.playlistfeature.domain.entity.Track
import com.yes.playlistfeature.presentation.model.TrackUI

class Mapper {
    fun map(track: Track): TrackUI {
        return TrackUI(
            track.id
        )
    }
}