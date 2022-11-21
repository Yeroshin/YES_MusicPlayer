package com.yes.trackdiialogfeature.data.mapper

import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.domain.MediaItem

class MediaMapper {
    fun mapToDomain(repositoryEntity: Map<String,String>): ArrayList<MediaItem> {
        val items= arrayListOf<MediaItem>()
        for (key in repositoryEntity){
            val item=MediaItem()
            item.title=key.key
            item.type=key.value
            items.add(item)
        }
        return items
    }
    fun mapToDomain(repositoryEntity: ArrayList<MediaEntity>): ArrayList<MediaItem> {
        val items= arrayListOf<MediaItem>()
        for (i in 0 until repositoryEntity.size){
            val mediaItem=MediaItem()
            mediaItem.title=repositoryEntity[i].title
            mediaItem.uri= repositoryEntity[i].uri
            items+=mediaItem
        }

        return items
    }

}