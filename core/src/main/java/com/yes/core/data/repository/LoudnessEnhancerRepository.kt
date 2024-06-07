package com.yes.core.data.repository

import android.media.audiofx.LoudnessEnhancer
import com.yes.core.data.mapper.Mapper

class LoudnessEnhancerRepository(
    private val loudnessEnhancer: LoudnessEnhancer,
    private val mapper: Mapper,
) {
    fun setEnabled(enabled:Boolean){
        try {
            loudnessEnhancer.enabled=enabled
        }catch (e:Exception){
            val et=e
        }
    }
    fun setTargetGain(percent:Int){
        loudnessEnhancer.setTargetGain(
            mapper.mapPercentToMillibels(
                percent,
                1500
            )
        )
    }
}