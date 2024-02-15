package com.yes.musicplayer.equalizer.data.repository

import com.yes.core.data.dataSource.SettingsDataStore
import kotlinx.coroutines.flow.first

class SettingsRepositoryImpl(
    private val settings: SettingsDataStore
) {

    suspend fun getEqualizerCustomPresetsName(): String {
        return settings.getEqualizerCustomPresetName().first()
    }
    suspend fun getEqualizerEnabled():Boolean{
        return settings.getEqualizerEnabled().first()
    }
    suspend fun getCurrentPreset():Int{
        return settings.getCurrentPreset().first()
    }


}