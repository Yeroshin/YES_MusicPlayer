package com.yes.player.data.repository

import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.player.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl (
    private val settingsDataSource: SettingsDataSource
): SettingsRepository {
    override suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        withContext(Dispatchers.IO){
            settingsDataSource.subscribe(CURRENT_PLAYLIST_ID ,1)
        }


}