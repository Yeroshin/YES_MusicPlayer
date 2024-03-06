package com.yes.musicplayer.equalizer.data.repository

import android.media.audiofx.LoudnessEnhancer
import com.yes.musicplayer.equalizer.data.mapper.Mapper
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract

class LoudnessEnhancerRepository(
    private val loudnessEnhancer: LoudnessEnhancer,
    private val mapper: Mapper
) {
    fun getTargetGain():Float{
        return loudnessEnhancer.targetGain
    }
    fun setTargetGain(percent:Int){

        loudnessEnhancer.setTargetGain(
            mapper.mapPercentToMillibels(
                percent,
                LoudnessEnhancer.PARAM_TARGET_GAIN_MB
            )
        )
    }
    fun setEnabled(enabled:Boolean){
        loudnessEnhancer.enabled=enabled
    }
}