package com.yes.musicplayer.equalizer.data.mapper

class Mapper {
    fun mapPercentToMillibels(percentMb:Int,maxMb:Int):Int{
        return percentMb*maxMb/100
    }
}