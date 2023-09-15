package com.yes.trackdialogfeature.data.mapper


import com.yes.core.domain.models.Track
import com.yes.core.repository.data.entity.MediaDataStoreEntity

class PlayListDaoMapper {
    fun map(track: MediaDataStoreEntity): Track {
        return Track(
            null,
            "",
            track.artist,
            track.title,
            track.data,
            track.duration,
            track.album,
            track.size
        )
    }
}