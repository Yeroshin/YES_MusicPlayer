package com.yes.trackdialogfeature.data.repository


import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.trackdialogfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {
    override suspend fun subscribeCurrentPlayListId(): Flow<Long> {
        return settingsDataSource.subscribe(CURRENT_PLAYLIST_ID, 1)
    }

    override suspend fun setCurrentPlayListId(id: Long) {
        settingsDataSource.set(id, CURRENT_PLAYLIST_ID)
    }

}