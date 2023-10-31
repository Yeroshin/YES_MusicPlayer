package com.yes.trackdialogfeature.data.mapper



import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.data.entity.MediaDataStoreEntity

class MediaRepositoryMapper {
    fun map(entity: MediaDataStoreEntity): Item {
        return Item(
            -1,
            entity.title,
            null,
            false
        )
    }
    fun mapToTrack(entity: MediaDataStoreEntity): PlayListDataBaseTrackEntity {
        return PlayListDataBaseTrackEntity(
            null,
            0,
            entity.artist,
            entity.title,
            entity.data,
            entity.duration,
            entity.album,
            entity.size,
            0
        )
    }
}