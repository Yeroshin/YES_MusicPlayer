package com.yes.alarmclockfeature.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.Flow


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) {
    suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()
}