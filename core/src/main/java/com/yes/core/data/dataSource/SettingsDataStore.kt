package com.yes.core.data.dataSource

import android.content.Context
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

class SettingsDataStore(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) {
    private object PreferencesKeys {
        val CURRENT_PLAYLIST_ID = longPreferencesKey("currentPlaylistId")
        val CURRENT_TRACK_INDEX = longPreferencesKey("currentTrackIndex")
        val CUSTOM_PRESET_NAME = stringPreferencesKey("customPresetNames")
        val EQUALIZER_ENABLED = booleanPreferencesKey("equalizerEnabled")
        val CURRENT_PRESET = intPreferencesKey("currentPreset")
        val CUSTOM_PRESET = stringPreferencesKey("customPreset")
        val LOUDNESS_ENHANCER_ENABLED = booleanPreferencesKey("loudnessEnhancerEnabled")
        val LOUDNESS_ENHANCER_TARGET_GAIN = intPreferencesKey("loudnessEnhancerTargetGain")
    }

    suspend fun subscribeCurrentPlaylistId(): Flow<Long> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_PLAYLIST_ID] ?: run {
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

    suspend fun setCurrentTrackIndex(currentTrackIndex: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_TRACK_INDEX] = currentTrackIndex
        }
    }

    fun subscribeTrackIndex(): Flow<Long> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_TRACK_INDEX] ?: run {
                    setCurrentTrackIndex(-1)
                    -1
                }
            }.distinctUntilChanged()
    }

    fun getEqualizerCustomPresetName(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CUSTOM_PRESET_NAME]?: run {
                    val name = context
                        .resources
                        .getString(com.yes.coreui.R.string.custom)

                    addCustomPresetName(name)
                    name
                }
            }
    }

    private suspend fun addCustomPresetName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CUSTOM_PRESET_NAME] = name
        }
    }
    fun subscribeEqualizerEnabled():Flow<Boolean>{
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.EQUALIZER_ENABLED]?:run {
                    setEqualizerEnabled(false)
                    false
                }
            }.distinctUntilChanged()
    }
    suspend fun setEqualizerEnabled(enabled:Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EQUALIZER_ENABLED] = enabled
        }
    }
    fun getCurrentPreset():Flow<Short>{
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.CURRENT_PRESET]?.let{
                    it.toShort()
            }?:run {
                    setCurrentPreset(0)
                    0
                }
            }
    }
    suspend fun setCurrentPreset(preset:Int){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CURRENT_PRESET] =preset
        }
    }
    suspend fun setCustomPreset(customPreset: IntArray) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CUSTOM_PRESET] = customPreset.joinToString(",")
        }
    }
    suspend fun getCustomPreset():Flow<IntArray>{
       return dataStore.data
           .map {preferences ->
               preferences[PreferencesKeys.CUSTOM_PRESET]
                   ?.split(",")
                   ?.map { it.toInt() }
                   ?.toIntArray()
                   ?:run {
                   val customPreset= intArrayOf(0,0,0,0,0)
                   setCustomPreset(customPreset)
                   customPreset
               }
           }
    }

    suspend fun setLoudnessEnhancerEnabled(enabled:Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOUDNESS_ENHANCER_ENABLED] = enabled
        }
    }
    suspend fun getLoudnessEnhancerEnabled():Flow<Boolean>{
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.LOUDNESS_ENHANCER_ENABLED]?:run {
                    setLoudnessEnhancerEnabled(false)
                    false
                }
            }
    }
    suspend fun setLoudnessEnhancerTargetGain(gainMB:Int){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LOUDNESS_ENHANCER_TARGET_GAIN] = gainMB
        }
    }
    suspend fun getLoudnessEnhancerTargetGain():Flow<Int>{
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKeys.LOUDNESS_ENHANCER_TARGET_GAIN]?:run {
                    setLoudnessEnhancerTargetGain(0)
                    0
                }
            }
    }
}