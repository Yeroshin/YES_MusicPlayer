package com.yes.trackdialogfeature.data.mapper



import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.repository.entity.TrackEntity
import com.yes.core.repository.entity.MediaDataStoreEntity

class MediaRepositoryMapper {
    fun map(entity: MediaDataStoreEntity): Item {
        return Item(
            -1,
            entity.title,
            null,
            false
        )
    }
    fun mapToTrack(entity: MediaDataStoreEntity): TrackEntity {
        return TrackEntity(
            null,
            0,
            entity.artist,
            entity.title,
            entity.data,
            entity.duration,
            entity.album,
            entity.size
        )
    }
}