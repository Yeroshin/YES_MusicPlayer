package com.yes.musicplayer.equalizer.presentation.mapper

import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI

class MapperUI {
    fun map(equalizer: Equalizer): EqualizerUI {

        return EqualizerUI(
            equalizerEnabled = equalizer.equalizerEnabled,
            currentPreset = equalizer.currentPreset,
            presetsNames = equalizer.presetsNames,
            equalizerValues = equalizer.bandsLevelRange?.let {
                equalizer.equalizerValues?.mapIndexed { index, value ->
                    convertToPercent(
                        value,
                        equalizer.bandsLevelRange)
                }
            },
            equalizerValuesText = equalizer.equalizerValues?.map {
                it.toString()
            }
        )
    }

    private fun convertToPercent(bandLevel: Short, bandLevelRange: ShortArray): Int {
        val level = bandLevel - bandLevelRange[0]
        val max = bandLevelRange[1] - bandLevelRange[0]
        return (level * 100) / max
    }
}