package com.yes.trackdiialogfeature.data.mapper

import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.domain.MediaItem

class MediaMapper {
    fun mapToDomain(repositoryEntity: Map<String,String>): ArrayList<MediaItem> {
        val items= arrayListOf<MediaItem>()
        for (key in repositoryEntity){
            val item=MediaItem(key.value,key.key)

            items.add(item)
        }
        return items
    }
    fun mapToDomain(repositoryEntity: ArrayList<MediaEntity>): ArrayList<MediaItem> {
        val items= arrayListOf<MediaItem>()
        for (i in 0 until repositoryEntity.size){
            val mediaItem=MediaItem(repositoryEntity[i].type,repositoryEntity[i].title)
            items+=mediaItem
        }

        return items
    }

}