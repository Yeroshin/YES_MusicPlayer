package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.PlayListEntity
import com.yes.trackdialogfeature.data.repository.entity.TrackEntity
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

class MenuRepositoryMapper {

    fun map(dataStoreEntity:MenuDataStoreEntity ):Menu  {
        TODO("Not yet implemented")
    }
    fun mapToPlayListEntity (dataStoreEntity:MenuDataStoreEntity ): PlayListEntity {
        TODO("Not yet implemented")
    }
    fun mapToTrackEntity (mediaDataStoreEntity:MediaDataStoreEntity ): TrackEntity {
        TODO("Not yet implemented")
    }
    fun mapToItem(dataStoreEntity:MenuDataStoreEntity ): Item {
        TODO("Not yet implemented")
    }
    fun mapToItem(dataStoreEntity: MediaDataStoreEntity): Item {
        TODO("Not yet implemented")
    }
}