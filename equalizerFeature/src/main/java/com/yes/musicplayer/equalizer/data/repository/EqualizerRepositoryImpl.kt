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
    private val equalizerFactory:EqualizerRepositoryImpl.Factory,
   // private var equalizer:Equalizer,
    private val audioSessionId:Int
) {
  //  private var equalizer:Equalizer?=null
    private var equalizer:Equalizer?=equalizerFactory.create(audioSessionId)
    fun getPresets():List<String>{
        return equalizer?.run {
            (0 until numberOfPresets.toInt()).mapNotNull { index ->
                getPresetName(index.toShort())
            }
        } ?: emptyList()
    }
    fun usePreset(preset:Int){
        checkControl()
        try{
            equalizer?.usePreset(preset.toShort())
        }catch (exception: Exception){
            val pr=exception
        }
    }
    fun getBand(frequency:Int):Int{
        return equalizer?.getBand(frequency)?.toInt()?:0
    }
    fun getBandFreqRange(band:Short):IntArray{
        return equalizer?.getBandFreqRange(band)?: IntArray(0)
    }
    fun getBandLevelRange():IntArray{
        return equalizer?.bandLevelRange?.map { it.toInt() }?.toIntArray()?: IntArray(2)
    }

    fun getBandLevel(band:Int): Int {
        return equalizer?.getBandLevel(band.toShort())?.toInt()?:0
    }
    fun setBandLevel(band: Int,level:Int){
        checkControl()
        try{
            equalizer?.setBandLevel(
                band.toShort(),
                level.toShort()
            )
        }catch (exception: Exception){
            val pr=exception
        }
      //  equalizer.setBandLevel(band.toShort(),level.toShort())
    }
    fun setEnabled(enabled:Boolean){
       // checkControl()

        if(!enabled){
            equalizer?.enabled=enabled
          /*  equalizer?.release()
            equalizer=null*/
        }else{
          //  equalizer=equalizerFactory.create(audioSessionId)
            equalizer?.enabled=enabled
        }
    }
    private fun checkControl(){
        equalizer?.let {
            if(!it.hasControl()){
                it.release()
                try{
                    equalizer=equalizerFactory.create(audioSessionId)
                    // equalizer.enabled=true//tmp TODO remove this
                }catch (exception: Exception){
                    val pr=exception
                }
            }
        }

    }
    class Factory(){
        fun create(audioSessionId:Int):Equalizer{
            return Equalizer(0,audioSessionId)
        }
    }
}