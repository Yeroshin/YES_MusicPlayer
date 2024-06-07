package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper

import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.data.dataSource.MediaDataStore

class MediaRepositoryImpl(
    private val mediaDataStore: MediaDataStore,
    private val mediaRepositoryMapper: MediaRepositoryMapper,
) {
    fun getMediaItems(
        type: String,
        selectionType: String?,
        selectionName: String
    ): List<Item> {

        return mediaDataStore.getMediaItems(
            type,
            selectionType,
            selectionType?.let {
                arrayOf(selectionName)
            } ?: emptyArray(),
        ).map {
            mediaRepositoryMapper.map(it)
        }
    }

    fun getAudioItems(
        selectionType: String?,
        selectionName: String
    ): List<PlayListDataBaseTrackEntity> {

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