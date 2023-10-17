package com.yes.playlistdialogfeature.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yes.playlistdialogfeature.data.repository.entity.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val CURRENT_PLAYLIST_ID = longPreferencesKey("currentPlaylistId")
    }
    fun subscribeCurrentPlaylistId(): Flow<Long> =
        dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] ?: 0
            }
    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val currentPlaylistId = preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] ?: 0
        return UserPreferences(currentPlaylistId)
    }
    suspend fun updateCurrentPlaylistId(currentPlaylistId: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] = currentPlaylistId
        }
    }


}