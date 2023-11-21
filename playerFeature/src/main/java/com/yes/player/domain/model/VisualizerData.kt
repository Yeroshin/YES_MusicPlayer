package com.yes.player.domain.model

data class VisualizerData (
    val samplingRate: Int=20000,
    val magnitudes:FloatArray= floatArrayOf(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,),
    val frequencies:FloatArray= floatArrayOf(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,),
)