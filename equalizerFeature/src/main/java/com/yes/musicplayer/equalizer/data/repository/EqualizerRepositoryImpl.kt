package com.yes.musicplayer.equalizer.data.repository

import android.hardware.ConsumerIrManager.CarrierFrequencyRange
import android.media.audiofx.Equalizer


class EqualizerRepositoryImpl(
    private val equalizer:Equalizer
) {
    fun getPresets():List<String>{
        val numberOfPresets =
            equalizer.numberOfPresets.toInt() // Получение количества доступных предустановленных настроек
        val presetsList: MutableList<String> = ArrayList()
        for (i in 0 until numberOfPresets) {
            val presetName =
                equalizer.getPresetName(i.toShort()) // Получение имени предустановленной настройки
            presetsList.add(presetName)
        }
        return presetsList
    }
    fun setPresetValues(preset:Short){
        equalizer.usePreset(preset)
    }
    fun getBand(frequency:Int):Short{
        return equalizer.getBand(frequency)
    }
    fun getBandFreqRange(band:Short):IntArray{
        return equalizer.getBandFreqRange(band)
    }
}