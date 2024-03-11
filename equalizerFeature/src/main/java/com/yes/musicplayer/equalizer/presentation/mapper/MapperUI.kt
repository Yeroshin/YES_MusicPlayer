package com.yes.musicplayer.equalizer.presentation.mapper

import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI
import kotlin.math.abs

class MapperUI {
    private val loudnessEnhancerTargetGainMaxValue=1500
    fun mapLoudnessEnhancerTargetGainPercentToValue(percent:Int):Int{
        return percent
        //return percent*100/loudnessEnhancerTargetGainMaxValue
    }
    fun map(equalizer: Equalizer): EqualizerUI {

        return EqualizerUI(
            equalizerEnabled = equalizer.equalizerEnabled,
            currentPreset = equalizer.currentPreset?.toInt(),
            presetsNames = equalizer.presetsNames,
           /* equalizerValues = equalizer.bandsLevelRange?.let {
                equalizer.equalizerValues?.mapIndexed { index, value ->
                    convertToPercent(
                        value,
                        equalizer.bandsLevelRange)
                }
            },*/
            bandsLevelRange =equalizer.bandsLevelRange?.let {
                convertBandLevelRangeToUi(it)
            },
            equalizerValues = equalizer.equalizerValues?.let {equalizerValues->
                equalizer.bandsLevelRange?.let {
                    equalizerValues.map {
                        convertEqualizerValueToUi(it,equalizer.bandsLevelRange)
                    }.toIntArray()
                }
            },
            loudnessEnhancerEnabled = equalizer.loudnessEnhancerEnabled,
            loudnessEnhancerValue = equalizer.loudnessEnhancerValue
        )
    }
    fun mapUiEqualizerValueToDomain(value:Int,maxLevelRange: Int):Int{
        return value-(maxLevelRange/2)
    }
    private fun convertBandLevelRangeToUi(bandLevelRange: IntArray):Int{
        return bandLevelRange[1]*2
    }
    private fun convertEqualizerValueToUi(value:Int,bandLevelRange: IntArray):Int{
        return bandLevelRange[1]+ value
    }
    private fun convertToPercent(bandLevel: Int, bandLevelRange: IntArray): Int {
        val level = bandLevel - bandLevelRange[0]
        val max = bandLevelRange[1] - bandLevelRange[0]
        return (level * 100) / max
    }
}