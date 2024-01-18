package com.yes.playlistdialogfeature.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settings: SettingsDataStore
) {

    suspend fun subscribeCurrentPlaylistId(): Flow<Long> {
        return settings.subscribeCurrentPlaylistId()
    }

    suspend fun updateCurrentPlaylistId(currentPlaylistId: Long) {
        settings.setCurrentPlaylistId(currentPlaylistId)
    }
    suspend fun updateCurrentTrackIndex(currentTrackIndex: Int) {
        settings.setCurrentTrackIndex(currentTrackIndex)
    }
}