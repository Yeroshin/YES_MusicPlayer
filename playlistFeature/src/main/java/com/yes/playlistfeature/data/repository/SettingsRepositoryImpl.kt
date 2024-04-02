package com.yes.playlistfeature.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.playlistfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
): SettingsRepository {
    override suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()
    suspend fun subscribeCurrentTrackIndex(): Flow<Int>{
        return dataStore.subscribeTrackIndex().map { it.toInt() }
    }

    suspend fun setCurrentTrackIndex(currentTrackIndex: Int){
        dataStore.setCurrentTrackIndex(currentTrackIndex.toLong())
    }

}