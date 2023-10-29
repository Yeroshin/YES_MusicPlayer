package com.yes.playlistdialogfeature.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.playlistdialogfeature.data.repository.entity.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsRepositoryImpl(
    private val settings: SettingsDataStore
) {

    fun subscribeCurrentPlaylistId(): Flow<Long> {
        return settings.subscribeCurrentPlaylistId()
    }

    suspend fun updateCurrentPlaylistId(currentPlaylistId: Long) {
        settings.setCurrentPlaylistId(currentPlaylistId)
    }
}