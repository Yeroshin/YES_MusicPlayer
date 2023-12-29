package com.yes.alarmclockfeature.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.Flow


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) {
    fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()
}