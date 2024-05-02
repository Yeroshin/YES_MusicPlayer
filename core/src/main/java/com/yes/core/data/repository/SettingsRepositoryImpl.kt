package com.yes.core.data.repository

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_PRESET
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_TRACK_INDEX
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CUSTOM_PRESET
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.EQUALIZER_ENABLED
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.LOUDNESS_ENHANCER_ENABLED
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.LOUDNESS_ENHANCER_TARGET_GAIN
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.THEME
import com.yes.core.presentation.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) {
    object PreferencesKeys {
        val THEME = intPreferencesKey("theme")
        val CURRENT_PLAYLIST_ID = longPreferencesKey("currentPlaylistId")
        val CURRENT_TRACK_INDEX = intPreferencesKey("currentTrackIndex")
        val CUSTOM_PRESET_NAME = stringPreferencesKey("customPresetNames")
        val EQUALIZER_ENABLED = booleanPreferencesKey("equalizerEnabled")
        val CURRENT_PRESET = intPreferencesKey("currentPreset")
        val CUSTOM_PRESET = stringPreferencesKey("customPreset")
        val LOUDNESS_ENHANCER_ENABLED = booleanPreferencesKey("loudnessEnhancerEnabled")
        val LOUDNESS_ENHANCER_TARGET_GAIN = intPreferencesKey("loudnessEnhancerTargetGain")
    }

    fun subscribeCurrentPlaylistId(): Flow<Long> =
        settingsDataSource.subscribe(CURRENT_PLAYLIST_ID, 1)

    fun subscribeCurrentTrackIndex(): Flow<Int> =
        settingsDataSource.subscribe(CURRENT_TRACK_INDEX, -1)

    suspend fun setCurrentTrackIndex(currentTrackIndex: Int) {
        settingsDataSource.set(currentTrackIndex, CURRENT_TRACK_INDEX)
    }

    fun subscribeEqualizerEnabled(): Flow<Boolean> {
        return settingsDataSource.subscribe(EQUALIZER_ENABLED, false)
    }

    suspend fun getCurrentPreset(): Int {
        return settingsDataSource.subscribe(CURRENT_PRESET, 0).first()
    }

    suspend fun getCustomPreset(): IntArray {
        return settingsDataSource.subscribe(
            CUSTOM_PRESET,
            intArrayOf(0, 0, 0, 0, 0).joinToString(",")
        ).first()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }

    suspend fun getLoudnessEnhancerEnabled(): Boolean {
        return settingsDataSource.subscribe(LOUDNESS_ENHANCER_ENABLED, false).first()
    }

    suspend fun getLoudnessEnhancerTargetGain(): Int {
        return settingsDataSource.subscribe(LOUDNESS_ENHANCER_TARGET_GAIN, 0).first()
    }

    suspend fun getTheme(): Theme {
        return settingsDataSource.subscribe(THEME, 0).map {
            when (it) {
                0 -> Theme.DarkTheme
                1 -> Theme.LightTheme
                else -> Theme.DarkTheme
            }
        }.first()
    }

    suspend fun setTheme(theme: Theme) {

        settingsDataSource.set(
            when (theme) {
                Theme.DarkTheme -> 0
                Theme.LightTheme -> 1
            },
            CURRENT_TRACK_INDEX
        )
    }
}