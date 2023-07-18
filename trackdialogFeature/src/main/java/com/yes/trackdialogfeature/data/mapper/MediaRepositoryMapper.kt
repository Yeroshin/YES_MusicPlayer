package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

class MediaRepositoryMapper {
    fun map(id: Int, type: String?, entity: MediaDataStoreEntity): Item {
        return Item(
            id,
            entity.title,
            type,
            false
        )
    }
}