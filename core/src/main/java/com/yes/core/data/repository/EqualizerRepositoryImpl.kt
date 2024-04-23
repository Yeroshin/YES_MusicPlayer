package com.yes.core.data.repository

import android.media.audiofx.Equalizer

class EqualizerRepositoryImpl(
    private val equalizer: Equalizer
) {
  //  private var equalizer: Equalizer? = equalizerFactory.create()
    fun setBandLevel(band: Int, level: Int) {
        // checkControl()
        try {
            equalizer?.setBandLevel(
                band.toShort(),
                level.toShort()
            )
        } catch (exception: Exception) {
            val pr = exception
        }
        //  equalizer.setBandLevel(band.toShort(),level.toShort())
    }

    fun usePreset(preset: Int) {
        //   checkControl()
        try {
            equalizer?.usePreset(preset.toShort())
        } catch (exception: Exception) {
            val pr = exception
        }
    }
    fun getBand(frequency:Int):Int{
        return equalizer?.getBand(frequency)?.toInt()?:0
    }

    fun setEnabled(enabled: Boolean) {
        // checkControl()
        equalizer?.enabled = enabled
        /*  if(!enabled){
               equalizer?.enabled=enabled
               /*  equalizer?.release()
                 equalizer=null*/
           }else{
               //  equalizer=equalizerFactory.create(audioSessionId)
               equalizer?.enabled=enabled
           }*/
    }

   /* class Factory(private val audioSessionId: Int) {
        fun create(): Equalizer {
            return Equalizer(0, audioSessionId)
        }
    }*/
}