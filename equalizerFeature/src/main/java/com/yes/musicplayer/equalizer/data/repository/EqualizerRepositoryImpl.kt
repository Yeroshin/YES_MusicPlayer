package com.yes.musicplayer.equalizer.data.repository

import android.content.Context
import android.media.AudioManager
import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import androidx.annotation.OptIn
import androidx.core.content.ContextCompat.getSystemService
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer


class EqualizerRepositoryImpl(
    private var equalizer:Equalizer,
    private val audioSessionId:Int
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
        checkControl()
        try{
            equalizer.usePreset(preset.toShort())
        }catch (exception: Exception){
            val pr=exception
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
        checkControl()
        try{
            equalizer.setBandLevel(
                band.toShort(),
                level.toShort()
            )
        }catch (exception: Exception){
            val pr=exception
        }
      //  equalizer.setBandLevel(band.toShort(),level.toShort())
    }
    fun setEnabled(enabled:Boolean){
        checkControl()
        equalizer.enabled=enabled
        val a=equalizer.enabled
      //  val b=equalizer.hasControl()

    }
    private fun checkControl(){
        if(!equalizer.hasControl()){
            val a=equalizer.enabled
            val b=equalizer.numberOfPresets
            equalizer.release()
            try{
                equalizer=Equalizer(1000,audioSessionId)
               equalizer.enabled=true//tmp TODO remove this
            }catch (exception: Exception){
                val pr=exception
            }
        }
    }
}