package com.yes.player.presentation.mapper

import com.yes.player.domain.model.DurationCounter
import com.yes.player.presentation.model.InfoUI

class MapperUI {
    fun map(durationCounter: DurationCounter): InfoUI{
        return InfoUI(
            durationCounter = durationCounter.toString()
        )
    }
}