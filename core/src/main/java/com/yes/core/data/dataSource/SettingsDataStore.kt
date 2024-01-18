package com.yes.core.data.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val CURRENT_PLAYLIST_ID = longPreferencesKey("currentPlaylistId")
        val CURRENT_TRACK_INDEX = intPreferencesKey("currentTrackIndex")
    }

    suspend fun subscribeCurrentPlaylistId(): Flow<Long> {
       return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] ?:run{
                    setCurrentPlaylistId(1)
                    1
                }
            }.distinctUntilChanged()

    }



    suspend fun setCurrentPlaylistId(currentPlaylistId: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] = currentPlaylistId
        }
    }
    suspend fun setCurrentTrackIndex(currentTrackIndex:Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_TRACK_INDEX] = currentTrackIndex
        }
    }
    fun subscribeTrackIndex(): Flow<Int> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_TRACK_INDEX] ?: run {
                    setCurrentTrackIndex(-1)
                    -1
                }
            }.distinctUntilChanged()
    }
}