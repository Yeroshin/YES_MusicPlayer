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
        //  return settings.getEqualizerCustomPresetName().first()
    }

    fun subscribeEqualizerEnabled(): Flow<Boolean> {
        return settingsDataSource.subscribe(EQUALIZER_ENABLED, false)
        // return settings.subscribeEqualizerEnabled()
    }

    suspend fun setEqualizerEnabled(enabled: Boolean) {
        return settingsDataSource.set(enabled, EQUALIZER_ENABLED)
        // settings.setEqualizerEnabled(enabled)
    }

    suspend fun getCurrentPreset(): Int {
        return settingsDataSource.subscribe(CURRENT_PRESET, 0).first()
        //return settings.getCurrentPreset().first().toInt()
    }

    suspend fun setCurrentPreset(preset: Int) {
        settingsDataSource.set(preset, CURRENT_PRESET)
        //settings.setCurrentPreset(preset)
    }

    suspend fun setCustomPreset(customPreset: IntArray) {
        settingsDataSource.set(
            customPreset.joinToString(","),
            CUSTOM_PRESET
        )
        //settings.setCustomPreset(customPreset)
    }

    suspend fun getCustomPreset(): IntArray {
        return settingsDataSource.subscribe(
            CUSTOM_PRESET,
            intArrayOf(0, 0, 0, 0, 0).joinToString(",")
        ).first()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
        //return settings.getCustomPreset().first()
    }


    suspend fun setLoudnessEnhancerEnabled(enabled: Boolean) {
        settingsDataSource.set(enabled,LOUDNESS_ENHANCER_ENABLED)
       // settings.setLoudnessEnhancerEnabled(enabled)
    }

    suspend fun getLoudnessEnhancerEnabled(): Boolean {
        return settingsDataSource.subscribe(LOUDNESS_ENHANCER_ENABLED,false).first()
       // return settings.getLoudnessEnhancerEnabled().first()
    }

    suspend fun setLoudnessEnhancerTargetGain(percent: Int) {
        settingsDataSource.set(percent,LOUDNESS_ENHANCER_TARGET_GAIN)
       // settings.setLoudnessEnhancerTargetGain(percent)
    }

    suspend fun getLoudnessEnhancerTargetGain(): Int {
        return settingsDataSource.subscribe(LOUDNESS_ENHANCER_TARGET_GAIN, 0).first()
       // return settings.getLoudnessEnhancerTargetGain().first()
    }

}