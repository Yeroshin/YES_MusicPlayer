package com.yes.core.data.repository

import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CURRENT_TRACK_INDEX
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) {
    suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        settingsDataSource.subscribe(CURRENT_PLAYLIST_ID,1)
    suspend fun subscribeCurrentTrackIndex(): Flow<Int> =
        settingsDataSource.subscribe(CURRENT_TRACK_INDEX,-1)

    suspend fun setCurrentTrackIndex(currentTrackIndex: Int){
        settingsDataSource.set(currentTrackIndex, CURRENT_TRACK_INDEX)
    }
}