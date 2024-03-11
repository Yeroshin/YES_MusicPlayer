package com.yes.musicplayer.equalizer.data.repository

import androidx.datastore.preferences.core.edit
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
    suspend fun setEqualizerEnabled(enabled:Boolean){
        settings.setEqualizerEnabled(enabled)
    }
    suspend fun getCurrentPreset():Int{
        return settings.getCurrentPreset().first().toInt()
    }
    suspend fun setCurrentPreset(preset:Int){
        settings.setCurrentPreset(preset)
    }
    suspend fun setCustomPreset(customPreset: IntArray){
        settings.setCustomPreset(customPreset)
    }
    suspend fun getCustomPreset():IntArray{
        return settings.getCustomPreset().first()
    }


    suspend fun setLoudnessEnhancerEnabled(enabled:Boolean){
        settings.setLoudnessEnhancerEnabled(enabled)
    }
    suspend fun getLoudnessEnhancerEnabled():Boolean{
        return settings.getLoudnessEnhancerEnabled().first()
    }
    suspend fun setLoudnessEnhancerTargetGain(percent:Int){
        settings.setLoudnessEnhancerTargetGain(percent)
    }
    suspend fun getLoudnessEnhancerTargetGain():Int{
        return settings.getLoudnessEnhancerTargetGain().first()
    }

}