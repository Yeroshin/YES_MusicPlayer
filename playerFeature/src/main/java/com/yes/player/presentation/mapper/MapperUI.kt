package com.yes.player.presentation.mapper

import com.yes.player.domain.model.DurationCounter
import com.yes.player.presentation.model.InfoUI

class MapperUI {
    fun map(durationCounter: DurationCounter): InfoUI{
        return InfoUI(
            durationCounter = formatTime(durationCounter.data)
        )
    }
    private fun formatTime(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt() % 60
        val minutes = (milliseconds / (1000 * 60) % 60).toInt()
        return String.format("%02d:%02d",  minutes, seconds)
    }
}