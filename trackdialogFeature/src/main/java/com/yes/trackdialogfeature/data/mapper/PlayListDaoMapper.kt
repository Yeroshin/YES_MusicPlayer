package com.yes.trackdialogfeature.data.mapper


import com.yes.core.repository.entity.TrackEntity
import com.yes.core.repository.entity.MediaDataStoreEntity

class PlayListDaoMapper {
    fun map(track: MediaDataStoreEntity): TrackEntity {
        return TrackEntity(
            null,
            0,
            track.artist,
            track.title,
            track.data,
            track.duration,
            track.album,
            track.size,
            0
        )
    }
}