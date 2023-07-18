package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

class MediaRepositoryImpl(
    private val mediaDataStore: MediaDataStore,
    private val mediaRepositoryMapper: MediaRepositoryMapper,
) {
    fun getMenuItems(id: Int, type: String?, name: String): List<Item> {

        return mediaDataStore.getMediaItems(
            type
                ?.let { arrayOf(it) }
                ?: arrayOf(),
            type,
            type
                ?.let {
                    arrayOf(name)
                }
                ?: run {
                    emptyArray()
                },
        ).mapNotNull {
            mediaRepositoryMapper.map(id, type, it)
        }
    }
}