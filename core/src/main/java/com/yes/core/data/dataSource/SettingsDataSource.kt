package com.yes.core.data.dataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SettingsDataSource(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun <T>set(value:T,key:Preferences.Key<T>){
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    fun <T>subscribe(key:Preferences.Key<T>,defaultValue:T): Flow<T> {
        return dataStore.data
            .map { preferences ->
                preferences[key]?:run {
                    set(defaultValue,key)
                    defaultValue
                }
            }.distinctUntilChanged()
    }
}