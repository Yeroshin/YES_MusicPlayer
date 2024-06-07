package com.yes.playlistdialogfeature.data.repository

import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_TRACK_INDEX


import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) {

    suspend fun subscribeCurrentPlaylistId(): Flow<Long> {
        return settingsDataSource.subscribe(CURRENT_PLAYLIST_ID,1,)
    }

    suspend fun updateCurrentPlaylistId(currentPlaylistId: Long) {
        settingsDataSource.set(currentPlaylistId,CURRENT_PLAYLIST_ID)
    }
    suspend fun updateCurrentTrackIndex(currentTrackIndex: Int) {
        settingsDataSource.set(currentTrackIndex,CURRENT_TRACK_INDEX)
    }
}