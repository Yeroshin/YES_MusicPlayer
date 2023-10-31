package com.yes.playlistfeature.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.playlistfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
): SettingsRepository {
    override suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()

}