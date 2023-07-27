package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.entity.Track

class MediaRepositoryImpl(
    private val mediaDataStore: MediaDataStore,
    private val mediaRepositoryMapper: MediaRepositoryMapper,
) {
    fun getMenuItems(
        id: Int,
        type: String,
        selectionType: String?,
        selectionName: String
    ): List<Item> {

        return mediaDataStore.getMediaItems(
            arrayOf(type),
            selectionType,
            selectionType?.let {
                arrayOf(selectionName)
            } ?: emptyArray(),
        ).map {
            mediaRepositoryMapper.map(it).copy(menuId = id, type = type)
        }
    }

    fun getAudioItems(
        selectionType: String?,
        selectionName: String
    ): List<Track> {

        return mediaDataStore.getAudioItems(
            selectionType,
            selectionType?.let {
                arrayOf(selectionName)
            } ?: emptyArray(),
        ).map {
            mediaRepositoryMapper.mapToTrack(it)
        }
    }
}