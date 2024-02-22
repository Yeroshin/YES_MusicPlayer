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
    private val player:Int
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
    @OptIn(UnstableApi::class)
    fun usePreset(preset:Int){
        val a=equalizer.enabled
      /*  val b=equalizer.hasControl()
        val c=equalizer.enabled
        equalizer.enabled=true
        val d=equalizer.enabled*/
        val e=equalizer.hasControl()
        if(!e){
            equalizer.release()
            try{
                equalizer=Equalizer(1000,player)
            }catch (exception: Exception){
                // equalizer.release()
                val pr=preset.toShort()
            }
        }
        val z=equalizer.hasControl()
        try{
            equalizer.usePreset(1)
        }catch (exception: Exception){
           // equalizer.release()
            val pr=preset.toShort()
        }
        val s=equalizer.hasControl()
        try{
            equalizer.setBandLevel(
                0,
                0
            )
        }catch (exception: Exception){
            val pr=0
        }
        val x=equalizer.hasControl()

    }
    fun getBand(frequency:Int):Int{
        val a=equalizer.hasControl()
        var eq=0
         try{
            eq=equalizer.getBand(frequency).toInt()

        }catch (exception: Exception){
            // equalizer.release()
            val pr=exception
        }

        return eq
      //  return equalizer.getBand(frequency).toInt()

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
        val e=equalizer.hasControl()
        if(!e){
            equalizer.release()
            try{
                equalizer=Equalizer(1000,player)
            }catch (exception: Exception){
                // equalizer.release()
                val pr=exception
            }
        }
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