package com.yes.musicplayer.equalizer.presentation.mapper

import com.yes.musicplayer.equalizer.domain.entity.Equalizer
import com.yes.musicplayer.equalizer.presentation.model.EqualizerUI

class MapperUI {
    fun map(equalizer: Equalizer): EqualizerUI {
        return EqualizerUI(
            equalizer.enabled,
            currentPreset = equalizer.currentPreset,
            presets = equalizer.presetNames
        )
    }
}