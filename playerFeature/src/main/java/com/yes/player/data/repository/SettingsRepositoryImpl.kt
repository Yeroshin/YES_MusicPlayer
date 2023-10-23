package com.yes.player.data.repository

import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.player.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl (
    private val dataStore: SettingsDataStore
): SettingsRepository {
    override suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        withContext(Dispatchers.IO){
            dataStore.subscribeCurrentPlaylistId()
        }


}