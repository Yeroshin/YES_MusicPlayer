package com.yes.core.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) {
    suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()
    suspend fun subscribeCurrentTrackIndex(): Flow<Long> =
        dataStore.subscribeTrackIndex()
    suspend fun setCurrentTrackIndex(currentTrackIndex: Int){
        dataStore.setCurrentTrackIndex(currentTrackIndex.toLong())
    }
}