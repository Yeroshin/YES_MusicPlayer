package com.yes.musicplayer.equalizer.data.repository

import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import com.yes.musicplayer.equalizer.data.mapper.Mapper
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract

class LoudnessEnhancerRepository(
    private var loudnessEnhancer: LoudnessEnhancer,
    private val mapper: Mapper,
    private val  audioSessionId:Int
) {
    fun getTargetGain():Float{
        return loudnessEnhancer.targetGain
    }
    fun setTargetGain(percent:Int){
        checkControl()
        loudnessEnhancer.setTargetGain(
            mapper.mapPercentToMillibels(
                percent,
                1500
            )
        )
    }
    fun setEnabled(enabled:Boolean){
        try {
            loudnessEnhancer.enabled=enabled
        }catch (e:Exception){
            val et=e
            val b=et
        }

    }
    private fun checkControl(){
        if(!loudnessEnhancer.hasControl()){
            loudnessEnhancer.release()
            try{
                loudnessEnhancer= LoudnessEnhancer(audioSessionId)
                loudnessEnhancer.enabled=true//tmp
            }catch (exception: Exception){
                val pr=exception
            }
        }
    }
}