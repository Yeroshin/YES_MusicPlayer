package com.yes.playlistfeature.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.yes.core.repository.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
) {
    fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.subscribeCurrentPlaylistId()
}