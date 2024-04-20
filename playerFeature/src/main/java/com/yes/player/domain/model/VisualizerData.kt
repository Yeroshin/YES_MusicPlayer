package com.yes.player.domain.model

data class VisualizerData (
    val samplingRate: Int=20000,
    val magnitudes:FloatArray= floatArrayOf(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,),
    val frequencies:FloatArray= floatArrayOf(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,),
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VisualizerData) return false

        return samplingRate == other.samplingRate &&
                magnitudes.contentEquals(other.magnitudes) &&
                frequencies.contentEquals(other.frequencies)
    }

    override fun hashCode(): Int {
        var result = samplingRate
        result = 31 * result + magnitudes.contentHashCode()
        result = 31 * result + frequencies.contentHashCode()
        return result
    }
}