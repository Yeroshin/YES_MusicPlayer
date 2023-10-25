package com.yes.core.repository.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val CURRENT_PLAYLIST_ID = longPreferencesKey("currentPlaylistId")
    }

    suspend fun subscribeCurrentPlaylistId(): Flow<Long> =
      //  withContext(Dispatchers.IO){
            dataStore.data
                .map { preferences ->
                    preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] ?: 1
                }
       // }


    suspend fun setCurrentPlaylistId(currentPlaylistId: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] = currentPlaylistId
        }
    }
}