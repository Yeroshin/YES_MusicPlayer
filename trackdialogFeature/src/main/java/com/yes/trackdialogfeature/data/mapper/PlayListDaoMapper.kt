package com.yes.trackdialogfeature.data.mapper


import com.yes.core.repository.entity.PlayListDataBaseTrackEntity
import com.yes.core.repository.entity.MediaDataStoreEntity

class PlayListDaoMapper {
    fun map(track: MediaDataStoreEntity): PlayListDataBaseTrackEntity {
        return PlayListDataBaseTrackEntity(
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