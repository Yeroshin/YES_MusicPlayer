package com.yes.core.data.repository

import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CURRENT_TRACK_INDEX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) {
    fun subscribeCurrentPlaylistId(): Flow<Long> =
        settingsDataSource.subscribe(CURRENT_PLAYLIST_ID,1)
    fun subscribeCurrentTrackIndex(): Flow<Int> =
        settingsDataSource.subscribe(CURRENT_TRACK_INDEX,-1)

    suspend fun setCurrentTrackIndex(currentTrackIndex: Int){
        settingsDataSource.set(currentTrackIndex, CURRENT_TRACK_INDEX)
    }
    fun subscribeEqualizerEnabled(): Flow<Boolean> {
        return settingsDataSource.subscribe(SettingsDataSource.PreferencesKeys.EQUALIZER_ENABLED, false)
    }
    suspend fun getCurrentPreset(): Int {
        return settingsDataSource.subscribe(SettingsDataSource.PreferencesKeys.CURRENT_PRESET, 0).first()
    }
    suspend fun getCustomPreset(): IntArray {
        return settingsDataSource.subscribe(
            SettingsDataSource.PreferencesKeys.CUSTOM_PRESET,
            intArrayOf(0, 0, 0, 0, 0).joinToString(",")
        ).first()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
    }
    suspend fun getLoudnessEnhancerEnabled(): Boolean {
        return settingsDataSource.subscribe(SettingsDataSource.PreferencesKeys.LOUDNESS_ENHANCER_ENABLED,false).first()
    }
    suspend fun getLoudnessEnhancerTargetGain(): Int {
        return settingsDataSource.subscribe(SettingsDataSource.PreferencesKeys.LOUDNESS_ENHANCER_TARGET_GAIN, 0).first()
    }
}