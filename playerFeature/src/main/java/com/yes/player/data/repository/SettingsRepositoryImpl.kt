package com.yes.player.data.repository

import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.player.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl (
    private val dataStore: SettingsDataStore
): SettingsRepository {
    override fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()

}