package com.yes.playlistfeature.data.repository

import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.playlistfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
): SettingsRepository {
    override suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()

}