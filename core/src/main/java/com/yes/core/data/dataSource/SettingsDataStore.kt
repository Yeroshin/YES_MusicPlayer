package com.yes.core.data.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val CURRENT_PLAYLIST_ID = longPreferencesKey("currentPlaylistId")
    }

    fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] ?:run{
                    setCurrentPlaylistId(1)
                    1
                }
            }


    suspend fun setCurrentPlaylistId(currentPlaylistId: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] = currentPlaylistId
        }
    }
}