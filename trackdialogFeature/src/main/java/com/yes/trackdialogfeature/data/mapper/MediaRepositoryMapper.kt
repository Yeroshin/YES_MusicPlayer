package com.yes.trackdialogfeature.data.mapper



import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.domain.models.Track
import com.yes.core.repository.data.entity.MediaDataStoreEntity

class MediaRepositoryMapper {
    fun map(entity: MediaDataStoreEntity): Item {
        return Item(
            -1,
            entity.title,
            null,
            false
        )
    }
    fun mapToTrack(entity: MediaDataStoreEntity): Track {
        return Track(
            null,
            "",
            entity.artist,
            entity.title,
            entity.data,
            entity.duration,
            entity.album,
            entity.size
        )
    }
}