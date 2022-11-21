package com.yes.trackdiialogfeature.data.mapper

import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.domain.MediaItem

class StringMapper():Mapper<ArrayList<MediaItem>,ArrayList<String>> {
    override fun mapToDomain(repositoryEntity: ArrayList<String>): ArrayList<MediaItem> {
        val items= arrayListOf<MediaItem>()
        for (i in repositoryEntity){
            val item=MediaItem()
            item.title=
        }
        return items
    }

    override fun mapToRepository(domainEntity: ArrayList<MediaItem>): ArrayList<String> {
        TODO("Not yet implemented")
    }

}