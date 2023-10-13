package com.yes.playlistfeature.data.repository

import com.yes.core.repository.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) {
    fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()
}