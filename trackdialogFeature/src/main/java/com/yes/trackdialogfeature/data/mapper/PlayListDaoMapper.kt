package com.yes.trackdialogfeature.data.mapper


import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.data.entity.MediaDataStoreEntity

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