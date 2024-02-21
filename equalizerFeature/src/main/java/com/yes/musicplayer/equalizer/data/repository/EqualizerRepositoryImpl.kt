package com.yes.musicplayer.equalizer.data.repository

import android.content.Context
import android.media.AudioManager
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import androidx.core.content.ContextCompat.getSystemService


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
    fun usePreset(preset:Int){

        val pr=preset.toShort()
        val t=equalizer.enabled
        equalizer.enabled=true
        equalizer.setEnabled(true)
        val c=equalizer.hasControl()
       // equalizer.release()
        val f=equalizer.enabled
        try{
            equalizer.usePreset(1)
        }catch (exception: Exception){
           // equalizer.release()
            val pr=preset.toShort()
        }

    }
    fun getBand(frequency:Int):Int{
        return equalizer.getBand(frequency).toInt()

    }
    fun getBandFreqRange(band:Short):IntArray{
        return equalizer.getBandFreqRange(band)
    }
    fun getBandLevelRange():IntArray{
        return equalizer.bandLevelRange.map { it.toInt() }.toIntArray()
    }

    fun getBandLevel(band:Int): Int {
        return equalizer.getBandLevel(band.toShort()).toInt()
    }
    fun setBandLevel(band: Int,level:Int){
        try{
            equalizer.setBandLevel(
                band.toShort(),
                level.toShort()
            )
        }catch (exception: Exception){
            val pr=0
        }
      //  equalizer.setBandLevel(band.toShort(),level.toShort())
    }
}