package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Track

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