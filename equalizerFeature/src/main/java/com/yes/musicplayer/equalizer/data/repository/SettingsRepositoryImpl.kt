package com.yes.musicplayer.equalizer.data.repository

import android.content.Context
import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CUSTOM_PRESET_NAME
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.EQUALIZER_ENABLED
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CURRENT_PRESET
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.CUSTOM_PRESET
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.LOUDNESS_ENHANCER_ENABLED
import com.yes.core.data.dataSource.SettingsDataSource.PreferencesKeys.LOUDNESS_ENHANCER_TARGET_GAIN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource,
    private val context: Context
) {

    suspend fun getEqualizerCustomPresetsName(): String {
        return settingsDataSource.subscribe(
            CUSTOM_PRESET_NAME,
            context.resources.getString(com.yes.coreui.R.string.custom)
        ).first()
    }

    fun subscribeEqualizerEnabled(): Flow<Boolean> {
        return settingsDataSource.subscribe(EQUALIZER_ENABLED, false)
    }

    suspend fun setEqualizerEnabled(enabled: Boolean) {
        return settingsDataSource.set(enabled, EQUALIZER_ENABLED)
    }

    suspend fun getCurrentPreset(): Int {
        return settingsDataSource.subscribe(CURRENT_PRESET, 0).first()
    }

    suspend fun setCurrentPreset(preset: Int) {
        settingsDataSource.set(preset, CURRENT_PRESET)
    }

    suspend fun setCustomPreset(customPreset: IntArray) {
        settingsDataSource.set(
            customPreset.joinToString(","),
            CUSTOM_PRESET
        )
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


    suspend fun setLoudnessEnhancerEnabled(enabled: Boolean) {
        settingsDataSource.set(enabled,LOUDNESS_ENHANCER_ENABLED)
   }

    suspend fun getLoudnessEnhancerEnabled(): Boolean {
        return settingsDataSource.subscribe(LOUDNESS_ENHANCER_ENABLED,false).first()
   }

    suspend fun setLoudnessEnhancerTargetGain(percent: Int) {
        settingsDataSource.set(percent,LOUDNESS_ENHANCER_TARGET_GAIN)
    }

    suspend fun getLoudnessEnhancerTargetGain(): Int {
        return settingsDataSource.subscribe(LOUDNESS_ENHANCER_TARGET_GAIN, 0).first()
    }

}